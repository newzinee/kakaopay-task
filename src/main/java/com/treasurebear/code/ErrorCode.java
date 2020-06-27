package com.treasurebear.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    OK("ok", "SUCCESS"),
    ERROR("err00", "에러가 발생했습니다."),
    INVALID_REQUEST("err01", "요청값을 확인해주세요."),
    NEED_MORE_MONEY("err02", "인원수보다 많은 금액을 입력하세요."),
    INVALID_USER("err03", "사용자 정보가 없습니다."),
    CANNOT_DUPLICATE("err04", "중복해서 받을 수 없습니다.")


    ;
    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.message = message;
        this.code = code;
    }

}
