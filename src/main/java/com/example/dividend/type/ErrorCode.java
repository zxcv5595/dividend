package com.example.dividend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_EXIST_COMPANY("존재하지 않는 회사 입니다"),
    INVALID_REQUEST("잘못된 요청 입니다."),
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생 했습니다."),
    ALREADY_SAVED_COMPANY("이미 저장된 회사 입니다.")
    ;
    private final String description;


}
