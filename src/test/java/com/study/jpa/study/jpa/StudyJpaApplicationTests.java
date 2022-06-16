package com.study.jpa.study.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudyJpaApplicationTests {

    @Autowired
    private EntityManager em;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAndSave() {
        System.out.println("false");
    }

    @Test
    public void multiThreadJPADML() {
        System.out.println("false");
    }

}
