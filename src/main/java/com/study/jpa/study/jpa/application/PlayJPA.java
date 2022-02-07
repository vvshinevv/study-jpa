package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.entity.Member;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class PlayJPA {

    private final EntityManager em;

    public PlayJPA(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void doApplication() {
        Member member1 = new Member("A");
        Member member2 = new Member("B");
        Member member3 = new Member("C");

        System.out.println("========");
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        System.out.println("========");
    }
}
