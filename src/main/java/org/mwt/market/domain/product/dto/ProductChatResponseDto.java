package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductChatResponseDto {

    private final Long chatRoomId;
    private final String productThumbnail;
    private final String lastChatMessage;
    private final Long partnerId;
    private final String partnerNickname;
    private final String partnerProfileImage;

    @Builder
    public ProductChatResponseDto(Long chatRoomId, String productThumbnail, String lastChatMessage,
                                  Long partnerId, String partnerNickname, String partnerProfileImage) {
        this.chatRoomId = chatRoomId;
        this.productThumbnail = productThumbnail;
        this.lastChatMessage = lastChatMessage;
        this.partnerId = partnerId;
        this.partnerNickname = partnerNickname;
        this.partnerProfileImage = partnerProfileImage;
    }
}
