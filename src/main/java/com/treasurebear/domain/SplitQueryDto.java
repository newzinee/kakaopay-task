package com.treasurebear.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class SplitQueryDto {
    private Integer receiveMoney;
    private Long receiveUserId;

    @QueryProjection
    public SplitQueryDto(Integer receiveMoney, Long receiveUserId) {
        this.receiveMoney = receiveMoney;
        this.receiveUserId = receiveUserId;
    }
}
