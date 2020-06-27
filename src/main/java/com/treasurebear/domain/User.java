package com.treasurebear.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class User {

    private Long userId;
    private String roomId;

    @Builder
    public User(Long userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
