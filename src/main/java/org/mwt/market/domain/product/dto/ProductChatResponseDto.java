package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductChatResponseDto {

    private final Long chatRoomId;
    private final Long productId;
    private final String productImage;
    private final String productStatus;
    private final Long personId;
    private final String personNickname;
    private final String personProfileImage;
    private final String lastMessage;
    private final String lastCattedAt;

    @Builder
    public ProductChatResponseDto(Long chatRoomId, Long productId, String productImage,
        String productStatus, String lastChatMessage,
        Long personId, String personNickname, String personProfileImage,
        String lastCattedAt) {
        this.chatRoomId = chatRoomId;
        this.productId = productId;
        this.productImage = productImage;
        this.productStatus = productStatus;
        this.lastMessage = lastChatMessage;
        this.personId = personId;
        this.personNickname = personNickname;
        this.personProfileImage = personProfileImage;
        this.lastCattedAt = lastCattedAt;
    }
}
