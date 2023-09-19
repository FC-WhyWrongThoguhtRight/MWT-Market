package org.mwt.market.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwt.market.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Data JPA를 통해 데이터 저장")
    void insert() {
        User user = User.builder()
                .name("나야나")
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("Data JPA를 통해 데이터 읽기")
    void find() {
        List<User> all = userRepository.findAll();
        for (User user : all) {
            System.out.println(user.toString());
        }
    }

}