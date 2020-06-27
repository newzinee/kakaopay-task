package com.treasurebear.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Split extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "split_id")
    private Long id;
    private Integer money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spray_id")
    private Spray spray;

    @Embedded
    private User user;

    @Builder
    public Split(Integer money, Spray spray, User user) {
        this.money = money;
        this.spray = spray;
        this.user = user;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}
