package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

@Getter
public class ProductListResponseDto extends BaseResponseBody {

    private final Long id;
    private final String title;
    private final Integer price;
    private final String thumbnail;
    private final String status;
    private final Integer likes;
    private final boolean like;

    @Builder
    public ProductListResponseDto(Integer statusCode, String message, Long id, String title,
        Integer price, String thumbnail, String status, Integer likes, boolean like) {
        super(statusCode, message);
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.status = status;
        this.likes = likes;
        this.like = like;
    }
}
