package com.treasurebear.repository;

import com.treasurebear.domain.Spray;
import com.treasurebear.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprayRepositoryTest {

    @Autowired
    SprayRepository sprayRepository;

    @Test
    public void test() {
        Long userId = 123L;
        String roomId = "room";
        String token = "ABC";
        User user = User.builder()
                .roomId(roomId)
                .userId(userId)
                .build();

        Spray spray = sprayRepository.findByUserAndToken(user, token);
        assertThat(spray).isNull();
    }

    @Test
    public void test1() {
        Long userId = 123L;
        String roomId = "room";
        String token = "ABC";
        User user = User.builder()
                .roomId(roomId)
                .userId(userId)
                .build();

        sprayRepository.findByUserAndToken(user, token);
    }

}