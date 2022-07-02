package com.study.jpa.study.jpa.lock.mapper;

import com.study.jpa.study.jpa.lock.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> findAll();

    Member findMemberById(Long id);
}
