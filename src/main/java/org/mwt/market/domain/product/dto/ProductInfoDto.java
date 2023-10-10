package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.product.entity.Product;

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

    public static ProductInfoDto toDto(Product product) {
        return ProductInfoDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .status(product.getStatus().getValue())
                .likes(product.getLikes())
                .like(false)
                .thumbnail(product.getProductAlbum().get(0).getUrl())
                .build();
    }
}
