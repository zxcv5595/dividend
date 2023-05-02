package com.example.dividend.dto;

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
}
