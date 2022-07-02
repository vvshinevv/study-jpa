package com.study.jpa.study.jpa.lock.application;

import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.Point;
import com.study.jpa.study.jpa.lock.mapper.MemberMapper;
import com.study.jpa.study.jpa.lock.mapper.PointMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = {"classpath:sql/schema-mysql.sql", "classpath:sql/data-mysql.sql"})
@SpringBootTest
class PointWithMybatisServiceTest {
    @Autowired
    private PointWithMybatisService pointWithMybatisService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PointMapper pointMapper;

    @Test
    void 포인트_적립_테스트() {
        List<Member> members = memberMapper.findAll();
        Member targetMember = members.get(0);
        pointWithMybatisService.depositPoint(targetMember.getId(), 10);

        Point expected = pointMapper.findPointById(targetMember.getId());
        assertThat(expected.getAmount()).isEqualTo(10);
    }
}
