package com.example.dividend.domain;

import com.example.dividend.dto.DividendDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DIVIDEND")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId","date"}
                )
        }
)
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
