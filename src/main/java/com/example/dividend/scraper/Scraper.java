package com.example.dividend.scraper;

import com.example.dividend.dto.CompanyDto;
import com.example.dividend.dto.ScrapedResult;

public interface Scraper {
    CompanyDto scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(CompanyDto companyDto);
}
