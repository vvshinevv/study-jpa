package com.study.jpa.study.jpa;

import com.study.jpa.study.jpa.entity.Member;
import com.study.jpa.study.jpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
class StudyJpaApplicationTests {

    @Autowired
    private EntityManager em;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testGetAndSave() {
        Team team = new Team("team1", "팀1");
        em.persist(team);

        Member member1 = new Member("회원1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member("회원2");
        member2.setTeam(team);
        em.persist(member2);

        Member findMember = em.find(Member.class, member1.getId());
        Team findTeam = findMember.getTeam();

        System.out.println("findMember :: " + findMember.getName());
        System.out.println("findTeam :: " + findTeam.getId());
        System.out.println("findTeam :: " + findTeam.getName());

        String jpql = "select m from Member m join m.team t where t.name = :teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getName());
        }
    }
}
