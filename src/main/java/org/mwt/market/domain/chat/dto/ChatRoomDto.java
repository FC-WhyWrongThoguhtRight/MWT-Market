package org.mwt.market.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomDto {

    private final Long chatRoomId;
    private final Long buyerId;
    private final String nickName;
    private final String buyerProfileImg;
    private final String lastMessage;
    private final LocalDateTime lastCreatedAt;

    @Builder
    public ChatRoomDto(Long chatRoomId, Long buyerId, String nickName, String buyerProfileImg,
        String lastMessage, LocalDateTime lastCreatedAt) {
        this.chatRoomId = chatRoomId;
        this.buyerId = buyerId;
        this.nickName = nickName;
        this.buyerProfileImg = buyerProfileImg;
        this.lastMessage = lastMessage;
        this.lastCreatedAt = lastCreatedAt;
    }
}
