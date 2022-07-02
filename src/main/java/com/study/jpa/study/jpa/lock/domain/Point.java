package com.study.jpa.study.jpa.lock.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(getId(), point.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", amount=" + amount +
                '}';
    }
}
