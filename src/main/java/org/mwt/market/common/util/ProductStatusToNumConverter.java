package org.mwt.market.common.util;

import jakarta.persistence.AttributeConverter;
import org.mwt.market.domain.product.vo.ProductStatus;

public class ProductStatusToNumConverter implements AttributeConverter<ProductStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductStatus attribute) {
        return attribute.getDbIdx();
    }

    @Override
    public ProductStatus convertToEntityAttribute(Integer dbData) {
        return ProductStatus.getFromDbIdx(dbData);
    }
}
