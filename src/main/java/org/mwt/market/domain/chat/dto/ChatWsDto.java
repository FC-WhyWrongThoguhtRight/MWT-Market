package org.mwt.market.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ChatWsDto {

    @Getter
    @Builder
    public static class MessageResponse {

        private Long userId;
        private String content;
        private LocalDateTime dateTime;

        public MessageResponse() {
        }

        public MessageResponse(Long userId, String content, LocalDateTime dateTime) {
            this.userId = userId;
            this.content = content;
            this.dateTime = dateTime;
        }
    }

    @Getter
    public static class MessageRequest {

        private Long userId;
        private String content;
    }
}
