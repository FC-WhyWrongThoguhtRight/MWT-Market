package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

@Getter
public class ProductChatResponseDto {

    private final Long id;
    private final String thumbnail;
    private final String status;
    // 채팅방 관련 DTO를 받을 예정

    @Builder
    public ProductChatResponseDto(Integer statusCode, String message, Long id, String thumbnail,
        String status) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.status = status;
    }
}
