package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final String title;
    private final Integer price;
    private final String categoryName;
    private final String content;
    private final List<String> images;
    private final String status;
    private final Integer likes;
    private final Seller seller;
    private final List<ProductSimpleInfo> sellerProductInfos;
    private boolean isMyProduct;

    @Builder
    public ProductResponseDto(Long id, String title,
        Integer price, String categoryName, String content, List<String> images, String status,
        Integer likes, Seller seller, List<ProductSimpleInfo> sellerProductInfos, boolean isMyProduct) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryName = categoryName;
        this.content = content;
        this.images = images;
        this.status = status;
        this.likes = likes;
        this.seller = seller;
        this.sellerProductInfos = sellerProductInfos;
        this.isMyProduct = isMyProduct;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
            .categoryName(product.getCategoryName())
            .content(product.getContent())
            .id(product.getProductId())
            .likes(product.getLikes())
            .images(product.getImages())
            .price(product.getPrice())
            .seller(Seller.fromEntity(product.getSeller()))
            .status(product.getStatus().getValue())
            .title(product.getTitle())
            .isMyProduct(true)
            .build();
    }

    public static ProductResponseDto fromEntity(Product product, List<Product> sellerProductInfos) {
        return ProductResponseDto.builder()
            .categoryName(product.getCategoryName())
            .content(product.getContent())
            .id(product.getProductId())
            .likes(product.getLikes())
            .images(product.getImages())
            .price(product.getPrice())
            .seller(Seller.fromEntity(product.getSeller()))
            .sellerProductInfos(sellerProductInfos.stream()
                .map(ProductSimpleInfo::toDto)
                .toList())
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

    @Getter
    public static class ProductSimpleInfo {

        private final Long id;
        private final String title;
        private final Integer price;
        private final String thumbnail;

        @Builder
        public ProductSimpleInfo(Long id, String title, Integer price, String thumbnail) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.thumbnail = thumbnail;
        }

        public static ProductSimpleInfo toDto(Product product) {
            return ProductSimpleInfo.builder()
                .id(product.getProductId())
                .title(product.getTitle())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .build();

        }
    }

    public void setIsMyProduct(boolean isMyProduct) {
        this.isMyProduct = isMyProduct;
    }
}