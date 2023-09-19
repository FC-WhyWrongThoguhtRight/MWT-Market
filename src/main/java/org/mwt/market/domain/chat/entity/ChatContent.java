package org.mwt.market.domain.chat.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class ChatContent {
    @MongoId
    private ObjectId id;

    private String name;
    private String content;
}
