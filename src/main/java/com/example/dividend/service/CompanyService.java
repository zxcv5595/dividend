package com.example.dividend.service;

import com.example.dividend.domain.Company;
import com.example.dividend.domain.Dividend;
import com.example.dividend.dto.CompanyDto;
import com.example.dividend.dto.ScrapedResult;
import com.example.dividend.exception.ScrapException;
import com.example.dividend.repository.CompanyRepository;
import com.example.dividend.repository.DividendRepository;
import com.example.dividend.scraper.YahooFinanceScraper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dividend.type.ErrorCode.ALREADY_SAVED_COMPANY;
import static com.example.dividend.type.ErrorCode.NOT_EXIST_COMPANY;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final Trie trie;
    private final YahooFinanceScraper yahooFinanceScraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public CompanyDto save(String ticker) {
        boolean exists = companyRepository.existsByTicker(ticker);
        if (exists) {
            throw new ScrapException(ALREADY_SAVED_COMPANY, HttpStatus.BAD_REQUEST);
        }
        return storeCompanyAndDividend(ticker);
    }

    public Page<Company> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    private CompanyDto storeCompanyAndDividend(String ticker) {
        CompanyDto companyDto = yahooFinanceScraper.scrapCompanyByTicker(ticker);

        if (ObjectUtils.isEmpty(companyDto)) {
            throw new ScrapException(NOT_EXIST_COMPANY, HttpStatus.BAD_REQUEST);
        }

        Company company = companyRepository.save(new Company(companyDto));

        ScrapedResult scrapedResult = yahooFinanceScraper.scrap(companyDto);

        List<Dividend> dividendList = scrapedResult.getDividendDtoList().stream()
                .map(e -> new Dividend(company.getId(), e))
                .collect(Collectors.toList());

        dividendRepository.saveAll(dividendList);

        return companyDto;
    }

    public void autoCompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }

    public List<String> autoComplete(String keyword) {
        return (List<String>) trie.prefixMap(keyword).keySet()
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    public void deleteKeyword(String keyword) {
        trie.remove(keyword);
    }

    public List<String> getCompanyNameByKeyword(String keyword) {
        Pageable limit = PageRequest.of(0, 10);
        Page<Company> companies = companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);
        return companies.stream()
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    public String deleteCompany(String ticker) {
        Company company = companyRepository.findByTicker(ticker)
                .orElseThrow(() -> new ScrapException(NOT_EXIST_COMPANY, HttpStatus.BAD_REQUEST));
        dividendRepository.deleteByCompanyId(company.getId());
        companyRepository.delete(company);

        deleteKeyword(company.getName());

        return company.getName();
    }
}
