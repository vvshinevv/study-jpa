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
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "amount")
    private int amount;

    protected Point() {
    }

    public Point(Long memberId, int amount) {
        this.memberId = memberId;
        this.amount = amount;
    }

    public void depositPoint(int amount) {
        this.amount += amount;
    }
}
