package com.study.jpa.study.jpa.simple;

import com.study.jpa.study.jpa.lock.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Sql(scripts = {"classpath:sql/schema-mysql.sql", "classpath:sql/data-mysql.sql"})
@SpringBootTest
public class SimpleTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void JPQL_TEST() {
        Member member = new Member("멤버1");
        em.persist(member);

        em.flush();
        em.clear();

        Member persistMember = em.find(Member.class, member.getId());
        persistMember.setMemberName("수정된_멤버1");


        List<Member> persistMembers = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println(persistMembers);

    }

}
