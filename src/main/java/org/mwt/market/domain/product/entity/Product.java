package org.mwt.market.domain.product.entity;

import jakarta.persistence.*;
import org.mwt.market.common.util.BooleanToNumConverter;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;

    private String title;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductStatus status;

    private String sellPlace;

    @CreatedDate
    private LocalDateTime createdAt;

    @Convert(converter = BooleanToNumConverter.class)
    private boolean isDeleted;

    private LocalDateTime deletedAt;


}
