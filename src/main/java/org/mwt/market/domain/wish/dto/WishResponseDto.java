package org.mwt.market.domain.wish.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.wish.entity.Wish;

@Getter
public class WishResponseDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final String thumbnail;
    private final String status;
    private final Integer likes;

    @Builder
    public WishResponseDto(Long id, String title, Integer price, String thumbnail,
        String status, Integer likes) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.status = status;
        this.likes = likes;
    }

    public static WishResponseDto fromEntity(Wish wish) {
        return new WishResponseDto(
            wish.getWishId(),
            wish.getProductTitle(),
            wish.getProductPrice(),
            wish.getProductThumbnail(),
            wish.getProductStatus(),
            wish.getProductLikes()
        );
    }
}
