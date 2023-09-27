package org.mwt.market.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.chat.entity.ChatContent;
import org.mwt.market.domain.chat.entity.ChatRoom;

@Getter
public class ChatRoomDto {

    private final Long chatRoomId;
    private final Long buyerId;
    private final String nickName;
    private final String buyerProfileImg;
    private final String lastMessage;
    private final String lasteCreatedAt;

    @Builder
    public ChatRoomDto(Long chatRoomId, Long buyerId, String nickName, String buyerProfileImg,
        String lastMessage, String lasteCreatedAt) {
        this.chatRoomId = chatRoomId;
        this.buyerId = buyerId;
        this.nickName = nickName;
        this.buyerProfileImg = buyerProfileImg;
        this.lastMessage = lastMessage;
        this.lasteCreatedAt = lasteCreatedAt;
    }
}
