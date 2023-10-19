package org.mwt.market.domain.chat.repository;

import java.util.List;
import org.mwt.market.domain.chat.entity.ChatContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatContentRepository extends MongoRepository<ChatContent, String> {

    List<ChatContent> findAllByChatRoomIdOrderByCreateAtAsc(Long chatRoomId);

    ChatContent findFirstByChatRoomIdOrderByCreateAtDesc(Long chatRoomId);

}
