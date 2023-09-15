package org.mwt.market.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwt.market.domain.chat.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChatRepositoryTest {
    @Autowired
    private ChatRepository mongoRepository;

    @Test
    @DisplayName("Data MongoDB를 통해 데이터 저장")
    void insert() {
        Chat chat = Chat.builder()
                .name("라이언")
                .content("야놀자")
                .build();
        mongoRepository.insert(chat);
    }

    @Test
    @DisplayName("Data MongoDB를 통해 데이터 읽기")
    void find() {
        List<Chat> all = mongoRepository.findAll();
        for (Chat chat : all) {
            System.out.println(chat.toString());
        }
    }
}