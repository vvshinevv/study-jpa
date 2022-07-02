package com.study.jpa.study.jpa.lock.application;

import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.MemberRepository;
import com.study.jpa.study.jpa.lock.domain.Point;
import com.study.jpa.study.jpa.lock.domain.PointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointWithJPAService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    public PointWithJPAService(PointRepository pointRepository, MemberRepository memberRepository) {
        this.pointRepository = pointRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void depositPoint(Long memberId, int amount) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        Point point = pointRepository.findPointForUpdate(memberId)
                .orElse(new Point(member.getId(), 0));

        point.depositPoint(amount);
        Point persistPoint = pointRepository.save(point);
    }
}
