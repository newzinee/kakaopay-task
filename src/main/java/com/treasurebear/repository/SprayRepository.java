package com.treasurebear.repository;

import com.treasurebear.domain.Spray;
import com.treasurebear.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SprayRepository extends JpaRepository<Spray, Long> {

    @Query("select s from Spray s where s.user.userId = :#{#user.userId} and s.user.roomId = :#{#user.roomId} and s.token = :token")
    Spray findByUserAndToken(@Param("user") User user, String token);

    @Query("select s from Spray s where s.user.roomId = :roomId and s.token = :token")
    Spray findByRoomIdAndToken(String roomId, String token);

}
