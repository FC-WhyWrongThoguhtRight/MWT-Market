package org.mwt.market.domain.product.entity;

import jakarta.persistence.*;
import org.mwt.market.domain.product.vo.ProductCategoryType;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    private ProductCategoryType productCategoryType;
}
