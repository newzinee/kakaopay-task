package com.treasurebear.repository;

import com.treasurebear.domain.*;
import com.treasurebear.service.SprayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SplitRepositoryTest {

    @Autowired SplitRepository splitRepository;
    @Autowired
    SprayService sprayService;

    @Test
    public void test_success() {
        String roomId = "room1";
        User sprayUser = User.builder()
                .userId(123L)
                .roomId(roomId)
                .build();
        Spray spray = sprayService.createSpray(sprayUser, new SprayRequestDto(1000, 3));

        User receiveUser = User.builder()
                .userId(999L)
                .roomId(roomId)
                .build();

        Split receiveSplit = splitRepository.findSplitByToken(receiveUser, spray.getToken());
        assertThat(receiveSplit.getMoney()).isPositive();
        assertThat(receiveSplit.getSpray().getUser()).isEqualTo(sprayUser);
        assertThat(receiveSplit.getUser()).isNull();
    }

    @Test
    @DisplayName("뿌리기 당 한 사용자는 한 번만 받을 수 있다.")
    public void check() {
        String roomId = "room1";
        User sprayUser = User.builder()
                .userId(123L)
                .roomId(roomId)
                .build();
        Spray spray = sprayService.createSpray(sprayUser, new SprayRequestDto(1000, 3));

        User receiveUser = User.builder()
                .userId(999L)
                .roomId(roomId)
                .build();

        boolean call = splitRepository.existsSplitBySprayAndUser(spray, receiveUser);
        assertThat(call).isFalse();

    }

    @Test
    public void findSplitByToken() {
        String roomId = "room1";
        User sprayUser = User.builder()
                .userId(123L)
                .roomId(roomId)
                .build();
        Spray spray = sprayService.createSpray(sprayUser, new SprayRequestDto(1000, 3));

        User receiveUser = User.builder()
                .userId(999L)
                .roomId(roomId)
                .build();

        Split split = splitRepository.findSplitByToken(receiveUser, spray.getToken());
        split.updateUser(receiveUser);

        assertThat(split.getUser()).isEqualTo(receiveUser);
        System.out.println("split = " + split.getId() + ", " + split.getUser().getUserId());
    }

    @Test
    public void test1() {
        User user = User.builder()
                .userId(999L)
                .roomId("room1")
                .build();
        String token = "abcd";

        SprayQueryDto dto = splitRepository.findAllInfoByUserAndToken(user, token);
        assertThat(dto).isNull();
    }

}