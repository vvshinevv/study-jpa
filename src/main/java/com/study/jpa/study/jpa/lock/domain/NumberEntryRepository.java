package com.study.jpa.study.jpa.lock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface NumberEntryRepository extends JpaRepository<NumberEntry, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "2000")
    })
    @Query("select n from NumberEntry n where n.id = :id")
    Optional<NumberEntry> findByNumberIdLock(Long id);

    @Query("select n from NumberEntry n where n.id = :id")
    Optional<NumberEntry> findByNumberIdIsolation(Long id);
}
