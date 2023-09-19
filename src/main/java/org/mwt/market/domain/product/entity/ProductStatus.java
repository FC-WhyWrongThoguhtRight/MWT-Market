package org.mwt.market.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mwt.market.domain.product.vo.ProductStatusType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private ProductStatusType productStatusType;
}
