package org.mwt.market.domain.wish.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishResponseDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final String thumbnailImage;
    private final String status;
    private final Integer likes;

    @Builder
    public WishResponseDto(Long id, String title, Integer price, String thumbnailImage,
        String status, Integer likes) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnailImage = thumbnailImage;
        this.status = status;
        this.likes = likes;
    }
}
