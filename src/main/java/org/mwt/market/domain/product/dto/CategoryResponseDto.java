package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final Long id;
    private final String name;

    @Builder
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

