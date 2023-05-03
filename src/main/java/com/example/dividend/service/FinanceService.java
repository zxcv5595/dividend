package com.example.dividend.service;

import com.example.dividend.domain.Company;
import com.example.dividend.domain.Dividend;
import com.example.dividend.dto.CacheKey;
import com.example.dividend.dto.CompanyDto;
import com.example.dividend.dto.DividendDto;
import com.example.dividend.dto.ScrapedResult;
import com.example.dividend.exception.ScrapException;
import com.example.dividend.repository.CompanyRepository;
import com.example.dividend.repository.DividendRepository;
import com.example.dividend.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dividend.dto.CacheKey.*;
import static com.example.dividend.type.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = KEY_FINANCE)
    public ScrapedResult dividendByCompanyName(String companyName) {
        log.info("search company -> '{}'", companyName);
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new ScrapException(NOT_EXIST_COMPANY));

        List<Dividend> dividendEntities = dividendRepository.findByCompanyId(company.getId());

        CompanyDto companyDto = CompanyDto.fromEntity(company);

        List<DividendDto> dividendDtoList = dividendEntities.stream()
                .map(DividendDto::fromEntity)
                .collect(Collectors.toList());

        return ScrapedResult.builder()
                .company(companyDto)
                .dividendDtoList(dividendDtoList)
                .build();
    }
}
