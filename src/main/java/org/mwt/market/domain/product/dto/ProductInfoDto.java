package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductInfoDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final String thumbnail;
    private final String status;
    private final Integer likes;
    private final boolean like;

    @Builder
    public ProductInfoDto(Long id, String title, Integer price, String thumbnail,
        String status, Integer likes, boolean like) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.status = status;
        this.likes = likes;
        this.like = like;
    }
}