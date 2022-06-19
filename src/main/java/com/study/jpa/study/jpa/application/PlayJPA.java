package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.entity.User;
import com.study.jpa.study.jpa.entity.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayJPA {
    public static final int NUMBER = 100;
    private final UserRepository userRepository;

    public PlayJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void doApplication() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            users.add(new User("member" + i));
        }

        userRepository.saveAll(users);
    }
}
