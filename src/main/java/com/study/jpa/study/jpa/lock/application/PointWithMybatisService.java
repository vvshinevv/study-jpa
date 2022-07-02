package com.study.jpa.study.jpa.lock.application;

import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.Point;
import com.study.jpa.study.jpa.lock.mapper.MemberMapper;
import com.study.jpa.study.jpa.lock.mapper.PointMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointWithMybatisService {
    private final PointMapper pointMapper;
    private final MemberMapper memberMapper;

    public PointWithMybatisService(PointMapper pointMapper, MemberMapper memberMapper) {
        this.pointMapper = pointMapper;
        this.memberMapper = memberMapper;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void depositPoint(Long memberId, int amount) {
        Member member = memberMapper.findMemberById(memberId);

        Point point = pointMapper.findPointByIdForUpdate(member.getId());
        if (point == null) {
            Point persistPoint = new Point(member.getId(), amount);
            pointMapper.persistPoint(persistPoint);
            return;
        }

        point.depositPoint(amount);
        pointMapper.updatePoint(point);
    }
}
