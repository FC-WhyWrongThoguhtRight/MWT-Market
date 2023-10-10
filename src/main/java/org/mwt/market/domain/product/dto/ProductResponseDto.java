package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final Integer category;
    private final String content;
    private final List<String> images;
    private final String status;
    private final Integer likes;
    private final Seller seller;

    @Builder
    public ProductResponseDto(Long id, String title,
        Integer price, Integer category, String content, List<String> images, String status,
        Integer likes, Seller seller) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.content = content;
        this.images = images;
        this.status = status;
        this.likes = likes;
        this.seller = seller;
    }

    @Getter
    public static class Seller {

        private final Long sellerId;
        private final String profileImage;
        private final String nickname;

        @Builder
        public Seller(Long sellerId, String profileImage, String nickname) {
            this.sellerId = sellerId;
            this.profileImage = profileImage;
            this.nickname = nickname;
        }
    }
}
