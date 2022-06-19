package com.study.jpa.study.jpa.infra;

import com.study.jpa.study.jpa.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Async(value = "threadPoolTaskExecutor")
    public CompletableFuture<List<User>> findUsersException(long start, long end) {
        if (start == 0) {
            throw new RuntimeException();
        }
        log.info("what is entityManager :: {}", entityManager.hashCode());
        return CompletableFuture.supplyAsync(() -> Collections.singletonList(entityManager.find(User.class, start)));
    }

    public void saveUser(User user) {
        entityManager.persist(user);
    }
}
