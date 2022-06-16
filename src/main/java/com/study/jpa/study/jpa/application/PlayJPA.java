package com.study.jpa.study.jpa.application;

import com.study.jpa.study.jpa.entity.User;
import com.study.jpa.study.jpa.repository.MemberRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayJPA {
    private final MemberRepository memberRepository;

    public PlayJPA(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void doApplication() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("member" + i));
        }

        memberRepository.saveAll(users);
    }
}
