package com.treasurebear.service;

import com.treasurebear.domain.Split;
import com.treasurebear.domain.Spray;
import com.treasurebear.domain.SprayRequestDto;
import com.treasurebear.domain.User;
import com.treasurebear.repository.SplitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
class SprayServiceTest {

    @Autowired
    SprayService sprayService;

    @Autowired
    SplitRepository splitRepository;

    @Test
    public void testGetSplitMoney() {
        Integer sprayMoney = 7;
        Integer sprayNumber = 7;

        List<Integer> splitMoney = sprayService.getSplitMoney(sprayMoney, sprayNumber);

        assertThat(splitMoney.size()).isEqualTo(sprayNumber);
        assertThat(splitMoney.stream().mapToInt(Integer::intValue).sum()).isEqualTo(sprayMoney);
        assertThat(splitMoney).doesNotContain(0);
    }

    @Test
    public void testGetSplitMoney2() {
        Integer sprayMoney = 100;
        Integer sprayNumber = 3;

        List<Integer> splitMoney = sprayService.getSplitMoney(sprayMoney, sprayNumber);

        assertThat(splitMoney.size()).isEqualTo(sprayNumber);
        assertThat(splitMoney.stream().mapToInt(Integer::intValue).sum()).isEqualTo(sprayMoney);
        assertThat(splitMoney).doesNotContain(0);
    }

    @Test
    public void testGetSplitMoney3() {

        Integer sprayMoney = 4;
        Integer sprayNumber = 7;

        assertThatIllegalArgumentException().isThrownBy(() -> sprayService.getSplitMoney(sprayMoney, sprayNumber));

    }

    @Test
    @Transactional
    public void testGetSpray() {
        Long userId = 123L;
        String roomId = "room";
        Integer money = 1000;
        Integer sprayNumber = 3;
        SprayRequestDto requestDto = SprayRequestDto.builder()
                .sprayMoney(money)
                .sprayNumber(sprayNumber)
                .build();
        User user = User.builder()
                .userId(userId)
                .roomId(roomId)
                .build();

        Spray spray = sprayService.createSpray(user, requestDto);

        System.out.println("token = " + spray.getToken());
        assertThat(spray.getToken().length()).isEqualTo(3);
        assertThat(spray.getUser().getRoomId()).isEqualTo(roomId);
        assertThat(spray.getMoney()).isEqualTo(money);
        assertThat(spray.getUser().getUserId()).isEqualTo(userId);
        assertThat(spray.getCreatedDate()).isBefore(LocalDateTime.now());
        assertThat(spray.getLastModifiedDate()).isBefore(LocalDateTime.now());
        assertThat(spray.getId()).isNotNull();

        List<Split> splits = splitRepository.findAll();
        assertThat(splits.size()).isEqualTo(sprayNumber);
        assertThat(splits).extracting("spray").containsOnly(spray);
        assertThat(splits).extracting("money").isNotEqualTo(0);
        assertThat(splits).extracting("user").containsOnlyNulls();

    }

}