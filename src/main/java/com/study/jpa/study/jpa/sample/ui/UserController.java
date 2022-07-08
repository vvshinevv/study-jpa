package com.study.jpa.study.jpa.sample.ui;

import com.study.jpa.study.jpa.sample.application.UserService;
import com.study.jpa.study.jpa.sample.model.UsersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    @GetMapping("/graceful/shutdown")
    public ResponseEntity<String> gracefulShutdown() throws InterruptedException {
        log.info(">>>>>>>>>>>>> graceful shutdown start.");
        Thread.sleep(20000);
        log.info(">>>>>>>>>>>>> graceful shutdown end.");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
