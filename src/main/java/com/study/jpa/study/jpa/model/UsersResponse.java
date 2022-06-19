package com.study.jpa.study.jpa.model;

import lombok.Getter;

@Getter
public class UsersResponse {
    private int totalCount;

    public UsersResponse(int totalCount) {
        this.totalCount = totalCount;
    }
}
