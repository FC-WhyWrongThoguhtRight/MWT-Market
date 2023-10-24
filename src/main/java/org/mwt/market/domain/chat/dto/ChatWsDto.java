package org.mwt.market.domain.chat.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatWsDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponse {

        private Long userId;
        private String nickName;
        private String profileImage;
        private String content;
        private LocalDateTime dateTime;

    }

    @Getter
    public static class MessageRequest {

        @NotNull(message = "userId는 필수 항목 입니다.")
        private Long userId;
        private String content;
    }
}
