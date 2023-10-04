package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.common.response.BaseResponseBody;

@Slf4j
@Getter
public class ProductCategoryResponseDto extends BaseResponseBody {

    private final List<Category> categories;

    @Builder
    public ProductCategoryResponseDto(Integer statusCode, String message,
        List<Category> categories) {
        super(statusCode, message);
        this.categories = categories;
    }

    @Getter
    public static class Category {

        private final Long id;
        private final String name;

        @Builder
        public Category(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}


