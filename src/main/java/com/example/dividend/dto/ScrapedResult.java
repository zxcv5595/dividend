package com.example.dividend.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ScrapedResult {

    private CompanyDto company;
    private List<DividendDto> dividendDtoList;

    public ScrapedResult(){
        this.dividendDtoList = new ArrayList<>();
    }

}
