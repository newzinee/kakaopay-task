package com.treasurebear.service;

import com.treasurebear.domain.*;
import com.treasurebear.repository.SplitRepository;
import com.treasurebear.repository.SprayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SprayService {
    private final SprayRepository sprayRepository;
    private final SplitRepository splitRepository;

    @Transactional
    public Spray createSpray(User user, @Valid SprayRequestDto requestDto) {

        String token = "";
        while(true) {
            token = getTempToken();

            Spray tmpSpray = sprayRepository.findByUserAndToken(user, token);
            if(tmpSpray == null) break;
        }

        Spray spray = Spray.builder()
                .money(requestDto.getSprayMoney())
                .token(token)
                .user(user)
                .build();

        sprayRepository.save(spray);

        List<Integer> splitMoneys = getSplitMoney(requestDto.getSprayMoney(), requestDto.getSprayNumber());
        splitMoneys.forEach(money -> {
            Split split = Split.builder().money(money).spray(spray).build();
            splitRepository.save(split);
        });

        return spray;
    }


    private String getTempToken() {
        StringBuffer tmpToken = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int rIndex = random.nextInt(3);
            switch (rIndex) {
                case 0:// a-z
                    tmpToken.append((char) ((int) (random.nextInt(26)) + 'a'));
                    break;
                case 1:// A-Z
                    tmpToken.append((char) ((int) (random.nextInt(26)) + 'A'));
                    break;
                case 2:// 0-9
                    tmpToken.append((random.nextInt(10)));
                    break;
            }
        }
        return tmpToken.toString();
    }

    public List<Integer> getSplitMoney(Integer sprayMoney, Integer sprayNumber) {
        if(sprayMoney < sprayNumber) throw new IllegalArgumentException("인원수보다 많은 금액을 입력하세요.");

        Integer splitMoney;
        Integer splitNumber = sprayNumber;
        List<Integer> result = new ArrayList<>();
        for(int i=0; i<sprayNumber; i++) {
            if(i != sprayNumber -1) {
                splitMoney = sprayMoney / splitNumber--;
                result.add(splitMoney);
                sprayMoney -= splitMoney;
            } else {
                result.add(sprayMoney);
            }
        }
        return result;
    }
}
