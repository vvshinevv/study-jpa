package com.study.jpa.study.jpa.infra;

import com.querydsl.jpa.impl.JPAQuery;
import com.study.jpa.study.jpa.entity.User;
import com.study.jpa.study.jpa.entity.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.study.jpa.study.jpa.entity.QUser.user;

@Slf4j
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Async(value = "threadPoolTaskExecutor")
    public CompletableFuture<List<User>> findUsersException(long start, long end) {
        if (start == 0) {
            throw new RuntimeException();
        }
        log.info("what is entityManager :: {}", entityManager);
        return CompletableFuture.supplyAsync(() -> new JPAQuery<>(entityManager)
                .select(user)
                .from(user)
                .where(user.id.goe(start).and(user.id.loe(end)))
                .fetch()
        );
    }
}
