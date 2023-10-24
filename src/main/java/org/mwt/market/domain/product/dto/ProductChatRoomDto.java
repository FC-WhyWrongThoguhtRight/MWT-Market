package org.mwt.market.domain.product.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductChatRoomDto {

    private final Long chatRoomId;
    private final Long buyerId;
    private final String nickName;
    private final String buyerProfileImg;
    private final Long productId;
    private final String productImage;
    private final String lastMessage;
    private final LocalDateTime lastCreatedAt;
}
