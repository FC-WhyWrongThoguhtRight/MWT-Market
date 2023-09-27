package org.mwt.market.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomRequestDto {

    private final Long userId;
    private final Long productId;

    @Builder
    public ChatRoomRequestDto(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
