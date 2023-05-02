package com.example.dividend.domain;

import com.example.dividend.dto.CompanyDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "COMPANY")
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;
    private String name;

    public Company(CompanyDto companyDto){
        this.name= companyDto.getName();
        this.ticker= companyDto.getTicker();
    }


}
