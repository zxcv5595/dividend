package com.example.dividend.scheduler;

import com.example.dividend.domain.Company;
import com.example.dividend.domain.Dividend;
import com.example.dividend.dto.CompanyDto;
import com.example.dividend.dto.ScrapedResult;
import com.example.dividend.repository.CompanyRepository;
import com.example.dividend.repository.DividendRepository;
import com.example.dividend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper yahooFinanceScraper;

    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void YahooFinanceScheduling() {
        log.info("scraping scheduler is started ");
        List<Company> companies = companyRepository.findAll();

        for (var company : companies) {
            log.info("scraping scheduler is started -> '{}'", company.getName());
            ScrapedResult scrapedResult = yahooFinanceScraper
                    .scrap(CompanyDto.fromEntity(company));

            scrapedResult.getDividendDtoList().stream()
                    .map(e -> new Dividend(company.getId(), e))
                    .forEach(e -> {
                        boolean exists = dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if (!exists) {
                            dividendRepository.save(e);
                        }
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
