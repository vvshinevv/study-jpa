package com.study.jpa.study.jpa.sample.application;

import com.study.jpa.study.jpa.sample.domain.User;
import com.study.jpa.study.jpa.sample.domain.UserRepository;
import com.study.jpa.study.jpa.sample.model.UsersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersResponse getUsers() {
        List<CompletableFuture<List<User>>> usersCompletableFutures = new ArrayList<>();
        for (int i = 0; i < PlayJPA.NUMBER; i = i + 10) {
            CompletableFuture<List<User>> usersCompletableFuture = userRepository.findUsersException(i, i + 10)
                    .exceptionally(thr -> new ArrayList<>());
            usersCompletableFutures.add(usersCompletableFuture);
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        CompletableFuture.allOf(usersCompletableFutures.toArray(new CompletableFuture[0])).join();
        List<User> users = usersCompletableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        return new UsersResponse(users.size());
    }
}
