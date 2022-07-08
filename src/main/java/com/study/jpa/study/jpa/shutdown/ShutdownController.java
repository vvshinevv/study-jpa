package com.study.jpa.study.jpa.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ShutdownController {

    @GetMapping("tomcat/graceful/shutdown")
    public ResponseEntity<String> gracefulShutdown() throws InterruptedException {
        Thread.sleep(10000);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
