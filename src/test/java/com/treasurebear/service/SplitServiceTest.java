package com.treasurebear.service;

import com.treasurebear.domain.SplitQueryDto;
import com.treasurebear.domain.SprayQueryDto;
import com.treasurebear.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
class SplitServiceTest {

    @Autowired
    private SplitService splitService;

    @Test
    public void sprayAllInfo_success() {
        String roomId = "room1";
        String token = "UrU";

        User user = User.builder()
                .userId(123L)
                .roomId(roomId)
                .build();

        SprayQueryDto sprayQueryDto = splitService.sprayAllInfo(user, token);
        List<SplitQueryDto> receiveList = sprayQueryDto.getReceiveList();

        assertThat(sprayQueryDto.getSprayMoney()).isEqualTo(receiveList.stream().mapToInt(SplitQueryDto::getReceiveMoney).sum());
        assertThat(sprayQueryDto.getReceiveMoney()).isEqualTo(receiveList.stream().filter(dto -> dto.getReceiveUserId() != null).mapToInt(SplitQueryDto::getReceiveMoney).sum());

    }

    @Test
    public void sprayAllInfo_fail() {
        String roomId = "room1";
        String token = "UrU3";

        User user = User.builder()
                .userId(123L)
                .roomId(roomId)
                .build();

        assertThatIllegalArgumentException().isThrownBy(() -> splitService.sprayAllInfo(user, token));

    }
}