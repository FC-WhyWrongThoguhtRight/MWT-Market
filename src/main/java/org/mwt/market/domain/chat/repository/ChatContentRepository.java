package org.mwt.market.domain.chat.repository;

import org.mwt.market.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatContentRepository extends MongoRepository<ChatMessage, String> {
}
