package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.entity.User;
import com.study.jpa.study.jpa.infra.UserRepository;
import com.study.jpa.study.jpa.model.UsersResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
        }

        CompletableFuture.allOf(usersCompletableFutures.toArray(new CompletableFuture[0])).join();
        List<User> users = usersCompletableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new UsersResponse(users.size());
    }
}
