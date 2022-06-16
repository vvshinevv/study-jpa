package com.study.jpa.study.jpa.repository;

import com.study.jpa.study.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User, Long> {
}
