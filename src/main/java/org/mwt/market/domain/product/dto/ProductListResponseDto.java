package org.mwt.market.domain.product.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductListResponseDto {

    private final List<ProductInfoDto> productInfos;

    private ProductListResponseDto(List<ProductInfoDto> productInfos) {
        this.productInfos = productInfos;
    }

    public static ProductListResponseDto of(List<ProductInfoDto> productInfos) {
        return new ProductListResponseDto(productInfos);
    }
}
