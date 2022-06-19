package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.entity.User;
import com.study.jpa.study.jpa.infra.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class PlayJPA {
    public static final int NUMBER = 100;

    private UserRepository userRepository;

    public PlayJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void doApplication() {
        for (int i = 0; i < NUMBER; i++) {
            userRepository.saveUser(new User("member" + i));
        }
    }
}
