package com.example.dividend.dto;

import com.example.dividend.domain.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    private String ticker;
    private String name;

}
