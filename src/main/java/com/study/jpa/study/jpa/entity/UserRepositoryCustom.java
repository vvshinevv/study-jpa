package com.study.jpa.study.jpa.entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserRepositoryCustom {
    CompletableFuture<List<User>> findUsersException(long start, long end);
}
