package com.study.jpa.study.jpa.simple;

import com.study.jpa.study.jpa.lock.application.NumberEntryService;
import com.study.jpa.study.jpa.lock.domain.Member;
import com.study.jpa.study.jpa.lock.domain.NumberEntry;
import com.study.jpa.study.jpa.lock.domain.NumberEntryRepository;
import org.junit.Ignore;
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

import static java.lang.Thread.sleep;

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
    void LOCK_TEST_WITH_NORMAL() throws InterruptedException {
        NumberEntry numberEntry = new NumberEntry();
        persistEntry = numberEntryRepository.save(numberEntry);

        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            es.execute(() -> {
                numberEntryService.normalIncrement(persistEntry.getId());
                latch.countDown();
            });
        }

        latch.await();
        System.out.println(numberEntryRepository.findById(persistEntry.getId()).orElse(new NumberEntry()).getCount());
    }

    @Test
    void LOCK_TEST_PESSIMISTIC_WRITE_TEST() throws InterruptedException {
        NumberEntry numberEntry = new NumberEntry();
        persistEntry = numberEntryRepository.save(numberEntry);

        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            es.execute(() -> {
                if (finalI == 0) {
                    try {
                        sleep(4000);

                    } catch (InterruptedException e) {

                        throw new RuntimeException(e);
                    }
                }
                System.out.println(">>>>>>>>> " + Thread.currentThread().getName());
                numberEntryService.lockIncrement(persistEntry.getId());
                latch.countDown();
            });
        }

        latch.await();
        System.out.println(numberEntryRepository.findById(persistEntry.getId()).orElse(new NumberEntry()).getCount());
    }

    @Test
    void LOCK_TEST_SERIALIZABLE_ISOLATION_LEVEL_TEST() throws InterruptedException {
        NumberEntry numberEntry = new NumberEntry();
        persistEntry = numberEntryRepository.save(numberEntry);

        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            es.execute(() -> {
                numberEntryService.isolationIncrement(persistEntry.getId());
                latch.countDown();
            });
        }

        latch.await();
        System.out.println(numberEntryRepository.findById(persistEntry.getId()).orElse(new NumberEntry()).getCount());
    }
}
