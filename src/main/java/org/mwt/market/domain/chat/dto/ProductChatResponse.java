package org.mwt.market.domain.chat.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.chat.entity.ChatRoom;

public class ProductChatResponse {


    @Getter
    public static class ProductChatResponseDto extends BaseResponseBody{

        private final List<ChatRoom> chatRooms;
        @Builder
        public ProductChatResponseDto(Integer statusCode, String message, List<ChatRoom> chatRooms) {
            super(statusCode, message);
            this.chatRooms = chatRooms;
        }
    }

}
