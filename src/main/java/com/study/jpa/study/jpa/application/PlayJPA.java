package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.domain.User;
import com.study.jpa.study.jpa.domain.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PlayJPA {
    public static final int NUMBER = 100;

    private UserRepository userRepository;

    public PlayJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void doApplication() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            users.add(new User("member" + i));
        }
        int[][] result = userRepository.saveBulkSplitInsert(users);
        System.out.println(Arrays.toString(result));
    }
}
