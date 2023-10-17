package org.mwt.market.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ChatWsDto {

    @Getter
    @Builder
    public static class MessageResponse {

        private String name;
        private String content;
        private LocalDateTime dateTime;

        public MessageResponse() {
        }

        public MessageResponse(String name, String content, LocalDateTime dateTime) {
            this.name = name;
            this.content = content;
            this.dateTime = dateTime;
        }
    }

    @Getter
    public static class MessageRequest {

        private String name;
        private String content;
    }
}
