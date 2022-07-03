package com.study.jpa.study.jpa.lock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("select p from Point p where p.memberId = :memberId")
    Optional<Point> findPointForUpdate(Long memberId);
    Optional<Point> findPointByMemberId(Long memberId);
}
