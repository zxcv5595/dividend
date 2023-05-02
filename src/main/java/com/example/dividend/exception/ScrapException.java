package com.example.dividend.exception;

import com.example.dividend.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrapException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ScrapException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();

    }
}
