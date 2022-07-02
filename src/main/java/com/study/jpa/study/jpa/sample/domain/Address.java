package com.study.jpa.study.jpa.sample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;

    private String location;

    protected Address() {
    }

    public Address(String location) {
        this.location = location;
    }

    public Address(Long id, String location) {
        this.id = id;
        this.location = location;
    }
}
