package com.study.jpa.study.jpa.lock.application;

import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.MemberRepository;
import com.study.jpa.study.jpa.lock.domain.Point;
import com.study.jpa.study.jpa.lock.domain.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = {"classpath:sql/schema-mysql.sql", "classpath:sql/data-mysql.sql"})
@SpringBootTest
class PointWithJPAServiceTest {
    @Autowired
    private PointWithJPAService pointWithJPAService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PointRepository pointRepository;

    @Test
    void 포인트_적립_테스트() {
        List<Member> members = memberRepository.findAll();
        Member targetMember = members.get(0);

        pointWithJPAService.depositPoint(targetMember.getId(), 10);
        Point depositPoint = pointRepository.findPointByMemberId(targetMember.getId()).orElseThrow();
        assertThat(depositPoint.getAmount()).isEqualTo(10);

        pointWithJPAService.withdrawPoint(targetMember.getId(), 2);
        Point withdrawPoint = pointRepository.findPointByMemberId(targetMember.getId()).orElseThrow();
        assertThat(withdrawPoint.getAmount()).isEqualTo(8);
    }
}
