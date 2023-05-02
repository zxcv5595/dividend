package com.example.dividend.dto;

import com.example.dividend.domain.Dividend;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DividendDto {
    private LocalDateTime date;
    private String dividend;

    public static DividendDto fromEntity(Dividend dividend){
        return DividendDto.builder()
                .date(dividend.getDate())
                .dividend(dividend.getDividend())
                .build();
    }
}
