package com.example.dividend.exception;

import com.example.dividend.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public SecurityException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();

    }
}
