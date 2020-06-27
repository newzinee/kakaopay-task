package com.treasurebear.service;


import com.treasurebear.domain.*;
import com.treasurebear.repository.SplitRepository;
import com.treasurebear.repository.SprayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SplitService {

    private final SprayRepository sprayRepository;
    private final SplitRepository splitRepository;

    public Split getSplit(User receiveUser, String token) throws CloneNotSupportedException {

        Spray spray = sprayRepository.findByRoomIdAndToken(receiveUser.getRoomId(), token);

        // check 1. 중복 여부
        boolean exists = splitRepository.existsSplitBySprayAndUser(spray, receiveUser);
        if(exists) {
            throw new CloneNotSupportedException("같은 뿌리기는 한 번만 받을 수 있습니다.");
        }

        // check 2. 기타 조건에 부합하는 결과
        Split split = splitRepository.findSplitByToken(receiveUser, token);
        if(split == null) {
            // 받기 종료
            throw new IllegalArgumentException("받기가 종료 되었습니다.");
        }

        split.updateUser(receiveUser);
        return split;
    }

    public SprayQueryDto sprayAllInfo(User user, String token) {

        SprayQueryDto info = splitRepository.findAllInfoByUserAndToken(user, token);
        if(info == null) throw new IllegalArgumentException();
        info.setReceiveList(splitRepository.findReceiveByUserAndToken(user, token));
        info.setReceiveMoney(info.getReceiveList().stream().mapToInt(SplitQueryDto::getReceiveMoney).sum());

        return info;
    }
}
