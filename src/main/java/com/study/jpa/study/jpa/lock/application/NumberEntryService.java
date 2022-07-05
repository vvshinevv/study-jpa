package com.study.jpa.study.jpa.lock.application;

import com.study.jpa.study.jpa.lock.domain.NumberEntry;
import com.study.jpa.study.jpa.lock.domain.NumberEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NumberEntryService {
    private final NumberEntryRepository numberEntryRepository;

    public NumberEntryService(NumberEntryRepository numberEntryRepository) {
        this.numberEntryRepository = numberEntryRepository;
    }

    @Transactional
    public void normalIncrement(Long id) {
        NumberEntry numberEntry = numberEntryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        numberEntry.increment();
        System.out.println(Thread.currentThread().getName() + ": " + numberEntry.getCount());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void isolationIncrement(Long id) {
        NumberEntry numberEntry = numberEntryRepository.findByNumberIdIsolation(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        numberEntry.increment();
        System.out.println(Thread.currentThread().getName() + ": " + numberEntry.getCount());
    }

    @Transactional
    public void lockIncrement(Long id) {
        NumberEntry numberEntry = numberEntryRepository.findByNumberIdLock(id).orElseThrow(() -> {
            throw new RuntimeException();
        });

        numberEntry.increment();
        System.out.println(Thread.currentThread().getName() + ": " + numberEntry.getCount());
    }
}
