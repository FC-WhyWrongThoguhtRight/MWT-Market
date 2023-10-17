package org.mwt.market.domain.chat.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collation = "chat")
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
