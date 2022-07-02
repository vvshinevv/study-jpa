package com.study.jpa.study.jpa.sample.model;

import lombok.Getter;

@Getter
public class UsersResponse {
    private int totalCount;

    public UsersResponse(int totalCount) {
        this.totalCount = totalCount;
    }
}
