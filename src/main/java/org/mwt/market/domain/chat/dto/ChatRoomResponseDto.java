package org.mwt.market.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.chat.entity.ChatRoom;

@Getter
public class ChatRoomResponseDto extends BaseResponseBody {

    private ChatRoom chatRoom;

    @Builder
    public ChatRoomResponseDto(Integer statusCode, String message, ChatRoom chatRoom) {
        super(statusCode, message);
        this.chatRoom = chatRoom;
    }
}
