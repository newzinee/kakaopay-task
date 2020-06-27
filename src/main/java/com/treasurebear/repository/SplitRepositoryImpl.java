package com.treasurebear.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.treasurebear.domain.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.treasurebear.domain.QSplit.split;
import static com.treasurebear.domain.QSpray.spray;

public class SplitRepositoryImpl implements SplitRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public SplitRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Split findSplitByToken(User user, String token) {

        return queryFactory
                .selectFrom(split)
                .leftJoin(split.spray, spray).fetchJoin()
                .where(
                        spray.user.roomId.eq(user.getRoomId()),
                        spray.user.userId.ne(user.getUserId()),
                        spray.token.eq(token),
                        spray.createdDate.after(LocalDateTime.now().minusMinutes(10))
                )
               .fetchFirst();
    }

    @Override
    public SprayQueryDto findAllInfoByUserAndToken(User user, String token) {
        return queryFactory
                .select(new QSprayQueryDto(
                        spray.createdDate.as("sprayDate"),
                        spray.money.as("sprayMoney")
                        ))
                .from(split)
                .leftJoin(split.spray, spray)
                .where(
                        spray.user.userId.eq(user.getUserId()),
                        spray.token.eq(token),
                        spray.createdDate.after(LocalDateTime.now().minusDays(7)),
                        split.user.userId.isNotNull()
                )
                .fetchFirst();
    }

    @Override
    public List<SplitQueryDto> findReceiveByUserAndToken(User user, String token) {
        return queryFactory
                .select(new QSplitQueryDto(
                        split.money.as("receiveMoney"),
                        split.user.userId.as("receiveUserId")
                ))
                .from(split)
                .leftJoin(split.spray, spray)
                .where(
                        spray.user.userId.eq(user.getUserId()),
                        spray.token.eq(token),
                        spray.createdDate.after(LocalDateTime.now().minusDays(7))
                )
                .fetch();
    }
}
