package com.example.dividend.controller;

import com.example.dividend.dto.ScrapedResult;
import com.example.dividend.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping("/dividend/{companyName}")
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchFinance(@PathVariable String companyName){
        ScrapedResult result = financeService.dividendByCompanyName(companyName);

        return ResponseEntity.ok(result);
    }
}
