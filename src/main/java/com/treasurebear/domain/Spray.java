package com.treasurebear.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spray extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "spray_id")
    private Long id;
    private String token;
    private Integer money;

    @Embedded
    private User user;

    @Builder
    public Spray(String token, Integer money, User user) {
        this.token = token;
        this.money = money;
        this.user = user;
    }
}
