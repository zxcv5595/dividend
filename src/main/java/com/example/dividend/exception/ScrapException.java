package com.example.dividend.exception;

import com.example.dividend.type.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrapException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    private HttpStatus statusCode;

    public ScrapException(ErrorCode errorCode, HttpStatus status){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.statusCode=status;

    }
}
