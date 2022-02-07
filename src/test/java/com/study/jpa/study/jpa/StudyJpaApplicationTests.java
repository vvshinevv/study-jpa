package com.study.jpa.study.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
        System.out.println("false");
    }
}
