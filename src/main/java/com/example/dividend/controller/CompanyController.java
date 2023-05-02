package com.example.dividend.controller;

import com.example.dividend.domain.Company;
import com.example.dividend.dto.CompanyDto;
import com.example.dividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword){
        List<String> result = companyService.autoComplete(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable){
        Page<Company> companies = companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyDto request){
        String ticker = request.getTicker().trim();
        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker is empty");
        }
        CompanyDto companyDto = companyService.save(ticker);
        companyService.autoCompleteKeyword(companyDto.getName());

        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}
