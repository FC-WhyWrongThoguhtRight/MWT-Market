package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.Getter;

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
