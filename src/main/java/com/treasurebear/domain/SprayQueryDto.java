package com.treasurebear.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.treasurebear.code.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SprayQueryDto {

    private ErrorCode resultCode;

    private LocalDateTime sprayDate;
    private Integer sprayMoney;
    private Integer receiveMoney;
    private List<SplitQueryDto> receiveList = new ArrayList<>();

    @QueryProjection
    public SprayQueryDto(LocalDateTime sprayDate, Integer sprayMoney) {
        this.sprayDate = sprayDate;
        this.sprayMoney = sprayMoney;
    }

    @Override
    public String toString() {
        return "SprayQueryDto{" +
                "sprayDate=" + sprayDate +
                ", sprayMoney=" + sprayMoney +
                ", receiveMoney=" + receiveMoney +
                ", receiveList=" + receiveList +
                '}';
    }
}
