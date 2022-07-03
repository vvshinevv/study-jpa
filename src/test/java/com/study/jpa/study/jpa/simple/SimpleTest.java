package com.study.jpa.study.jpa.simple;

import com.study.jpa.study.jpa.lock.application.NumberEntryService;
import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.NumberEntry;
import com.study.jpa.study.jpa.lock.domain.NumberEntryRepository;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Sql(scripts = {"classpath:sql/schema-mysql.sql", "classpath:sql/data-mysql.sql"})
@SpringBootTest
public class SimpleTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NumberEntryService numberEntryService;

    @Autowired
    private NumberEntryRepository numberEntryRepository;

    private NumberEntry persistEntry;
    @BeforeEach
    void setup() {

    }

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

    @Ignore
    @Test
    void LOCK_TEST() {
        NumberEntry numberEntry = new NumberEntry();
        NumberEntry persistEntry = numberEntryRepository.save(numberEntry);

        ExecutorService es = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        IntStream.range(0, 10).forEach((i) -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                numberEntryService.normalIncrement(persistEntry.getId());
            }, es);
            futures.add(future);
        });

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        NumberEntry persistNumberEntry = em.find(NumberEntry.class, persistEntry.getId());
        System.out.println("result >>>>>>> " + persistNumberEntry.getCount());
    }

    @Test
    void LOCK_TEST_PARENT() throws InterruptedException {
        for (int i = 0 ; i < 10; i++) {
            LOCK_TEST_1();
        }
    }

    void LOCK_TEST_1() throws InterruptedException {
        NumberEntry numberEntry = new NumberEntry();
        persistEntry = numberEntryRepository.save(numberEntry);

        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0 ; i < 10; i++) {
            es.execute(() -> {
                numberEntryService.normalIncrement(persistEntry.getId());
                latch.countDown();
            });
        }

        latch.await();
        System.out.println(numberEntryRepository.findById(persistEntry.getId()).orElse(new NumberEntry()).getCount());
    }
}
