package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

@Getter
public class ProductChatResponseDto {

    private final Long chatRoomId;
    private final String productThumbnail;
    private final String lastChatMessage;
    private final Long youId;
    private final String youNickname;
    private final String youProfileImage;

    @Builder
    public ProductChatResponseDto(Long chatRoomId, String productThumbnail, String lastChatMessage,
        Long youId, String youNickname, String youProfileImage) {
        this.chatRoomId = chatRoomId;
        this.productThumbnail = productThumbnail;
        this.lastChatMessage = lastChatMessage;
        this.youId = youId;
        this.youNickname = youNickname;
        this.youProfileImage = youProfileImage;
    }
}
