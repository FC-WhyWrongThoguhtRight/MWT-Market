package org.mwt.market.domain;

import org.mwt.market.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager = "mongoTxManager")
public interface ChatRepository extends MongoRepository<Chat, String> {
}
