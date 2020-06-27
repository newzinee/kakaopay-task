package com.treasurebear.domain;

import com.treasurebear.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SplitGetResponseDto {
    private ErrorCode resultCode;
    private Integer receiveMoney;

    @Builder
    public SplitGetResponseDto(ErrorCode resultCode, Integer receiveMoney) {
        this.resultCode = resultCode;
        this.receiveMoney = receiveMoney;
    }
}
