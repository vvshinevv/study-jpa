package com.study.jpa.study.jpa.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserRepositoryCustom {
    CompletableFuture<List<User>> findUsersException(long start, long end);
    int[] saveBulkInsert(List<User> users);
    int[][] saveBulkSplitInsert(List<User> users);
}
