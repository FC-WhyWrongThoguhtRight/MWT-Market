package org.mwt.market.domain.chat.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Builder
@Getter
public class ChatContent {

    @MongoId
    private ObjectId id;
    private Long chatRoomId;
    private Long userId;
    private String content;
    private LocalDateTime createAt;
}
