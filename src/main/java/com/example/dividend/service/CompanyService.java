package com.example.dividend.service;

import com.example.dividend.domain.Company;
import com.example.dividend.domain.Dividend;
import com.example.dividend.dto.CompanyDto;
import com.example.dividend.dto.ScrapedResult;
import com.example.dividend.repository.CompanyRepository;
import com.example.dividend.repository.DividendRepository;
import com.example.dividend.scraper.YahooFinanceScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final YahooFinanceScraper yahooFinanceScraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public CompanyDto save(String ticker) {
        boolean exists = companyRepository.existsByTicker(ticker);
        if(exists){
            throw new RuntimeException("already exist ticker ->"+ticker);
        }
        return storeCompanyAndDividend(ticker);
    }

    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    private CompanyDto storeCompanyAndDividend(String ticker) {
        CompanyDto companyDto = yahooFinanceScraper.scrapCompanyByTicker(ticker);

        if (ObjectUtils.isEmpty(companyDto)) {
            throw new RuntimeException("failed to scrap ticker ->" + ticker);
        }

        Company company = companyRepository.save(new Company(companyDto));

        ScrapedResult scrapedResult = yahooFinanceScraper.scrap(companyDto);

        List<Dividend> dividendList = scrapedResult.getDividendDtoList().stream()
                .map(e -> new Dividend(company.getId(), e))
                .collect(Collectors.toList());

        dividendRepository.saveAll(dividendList);

        return companyDto;
    }

}
