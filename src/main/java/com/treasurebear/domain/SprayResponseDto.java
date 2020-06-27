package com.treasurebear.domain;

import com.treasurebear.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SprayResponseDto {
    private ErrorCode resultCode;
    private String token;

    @Builder
    public SprayResponseDto(ErrorCode resultCode, String token) {
        this.resultCode = resultCode;
        this.token = token;
    }
}
