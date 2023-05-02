package com.example.dividend.domain;

import com.example.dividend.dto.DividendDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DIVIDEND")
public class Dividend {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private LocalDateTime date;
    private String dividend;

    public Dividend(Long companyId, DividendDto dividendDto){
        this.companyId=companyId;
        this.date=dividendDto.getDate();
        this.dividend=dividendDto.getDividend();
    }
}
