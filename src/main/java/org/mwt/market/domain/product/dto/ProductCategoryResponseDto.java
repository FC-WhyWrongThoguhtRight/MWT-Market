package org.mwt.market.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.product.vo.ProductCategoryType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
public class ProductCategoryResponseDto extends BaseResponseBody {

    private final List<String> categories;

    @Builder
    public ProductCategoryResponseDto(Integer statusCode, String message) {
        super(statusCode, message);
        this.categories = Stream.of(ProductCategoryType.values())
                .map(ProductCategoryType::getValue)
                .collect(Collectors.toList());
    }
}


