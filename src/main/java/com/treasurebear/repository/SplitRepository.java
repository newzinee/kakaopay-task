package com.treasurebear.repository;

import com.treasurebear.domain.Split;
import com.treasurebear.domain.Spray;
import com.treasurebear.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SplitRepository extends JpaRepository<Split, Long>, SplitRepositoryCustom {

    boolean existsSplitBySprayAndUser(Spray spray, User user);
}
