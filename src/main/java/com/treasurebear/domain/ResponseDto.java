package com.treasurebear.domain;

import com.treasurebear.code.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDto {
    private ErrorCode resultCode;

    public ResponseDto(ErrorCode resultCode) {
        this.resultCode = resultCode;
    }
}
