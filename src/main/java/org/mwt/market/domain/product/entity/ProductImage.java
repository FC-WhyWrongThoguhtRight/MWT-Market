package org.mwt.market.domain.product.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String url;
    private Integer order;

    @CreatedDate
    private LocalDateTime createdAt;
}
