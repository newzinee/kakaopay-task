package com.treasurebear.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class SprayRequestDto {

    @NotNull
    @Min(1)
    private Integer sprayMoney;

    @NotNull
    @Min(1)
    private Integer sprayNumber;

    @Builder
    public SprayRequestDto(@NotEmpty @Min(1) Integer sprayMoney, @NotEmpty @Min(1) Integer sprayNumber) {
        this.sprayMoney = sprayMoney;
        this.sprayNumber = sprayNumber;
    }
}
