package org.mwt.market.domain.chat.entity;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class ChatContent {

    @MongoId
    private ObjectId id;
    private Long chatRoomId;
    private User sender;
    private String message;
    private LocalDateTime createAt;
}
