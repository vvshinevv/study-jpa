package com.study.jpa.study.jpa.sample.ui;

import com.study.jpa.study.jpa.sample.application.UserService;
import com.study.jpa.study.jpa.sample.model.UsersResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/async/test")
    public UsersResponse getUsers() {
        return userService.getUsers();
    }
}