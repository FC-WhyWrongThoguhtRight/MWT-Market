package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.product.entity.ProductCategory;

@Getter
public class CategoryResponseDto {

    private final Long id;
    private final String name;

    @Builder
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryResponseDto fromEntity(ProductCategory productCategory) {
        return new CategoryResponseDto(productCategory.getCategoryId(), productCategory.getName());
    }
}

