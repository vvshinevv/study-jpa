package com.study.jpa.study.jpa.sample.infra;

import com.study.jpa.study.jpa.sample.domain.User;
import com.study.jpa.study.jpa.sample.domain.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Async(value = "threadPoolTaskExecutor")
    @Override
    public CompletableFuture<List<User>> findUsersException(long start, long end) {
        if (start == 0) {
            throw new RuntimeException();
        }
        log.info("what is entityManager :: {}", entityManager.hashCode());
        return CompletableFuture.supplyAsync(() -> Collections.singletonList(entityManager.find(User.class, start)));
    }

    @Override
    @Transactional
    public int[] saveBulkInsert(List<User> users) {
        return jdbcTemplate.batchUpdate("INSERT INTO user (`username`) VALUES (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, users.get(i).getUsername());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

    @Override
    public int[][] saveBulkSplitInsert(List<User> users) {
        return jdbcTemplate.batchUpdate(
                "INSERT INTO user (`username`) VALUES (?)",
                users,
                50,
                (ps, argument) -> ps.setString(1, argument.getUsername())
        );
    }
}
