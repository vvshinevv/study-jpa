package com.study.jpa.study.jpa.lock.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "number_entry")
public class NumberEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number_entry_id")
    private Long id;

    @Column(name = "number_entry_count")
    private int count;

    public NumberEntry() {
    }

    public void increment() {
        this.count += 1;
    }
}
