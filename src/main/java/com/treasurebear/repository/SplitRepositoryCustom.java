package com.treasurebear.repository;

import com.treasurebear.domain.Split;
import com.treasurebear.domain.SplitQueryDto;
import com.treasurebear.domain.SprayQueryDto;
import com.treasurebear.domain.User;

import java.util.List;

public interface SplitRepositoryCustom {
    Split findSplitByToken(User user, String token);

    SprayQueryDto findAllInfoByUserAndToken(User user, String token);

    List<SplitQueryDto> findReceiveByUserAndToken(User user, String token);
}
