package com.study.jpa.study.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Async(value = "threadPoolTaskExecutor")
    CompletableFuture<List<User>> findUserByIdGreaterThanEqualAndIdLessThan(long start, long end);
}
