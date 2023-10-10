package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final Long categoryId;
    private final String content;
    private final List<String> images;
    private final String status;
    private final Integer likes;
    private final Seller seller;

    @Builder
    public ProductResponseDto(Long id, String title,
        Integer price, Long categoryId, String content, List<String> images, String status,
        Integer likes, Seller seller) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryId = categoryId;
        this.content = content;
        this.images = images;
        this.status = status;
        this.likes = likes;
        this.seller = seller;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
            .categoryId(product.getCategoryId())
            .content(product.getContent())
            .id(product.getId())
            .likes(product.getLikes())
            .images(product.getImages())
            .price(product.getPrice())
            .seller(Seller.fromEntity(product.getSeller()))
            .status(product.getStatus().getValue())
            .title(product.getTitle())
            .build();
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

        public static Seller fromEntity(User user) {
            return new Seller(user.getUserId(), user.getProfileImageUrl(), user.getNickname());
        }
    }
}
