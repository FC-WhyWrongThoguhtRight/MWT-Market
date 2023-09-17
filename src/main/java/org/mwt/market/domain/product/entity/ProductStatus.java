package org.mwt.market.domain.product.entity;

import jakarta.persistence.*;
import org.mwt.market.domain.product.vo.ProductStatusType;

@Entity
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Enumerated(EnumType.STRING)
    private ProductStatusType productStatusType;
}
