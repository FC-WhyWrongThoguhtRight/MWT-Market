package org.mwt.market.domain.wish.entity;

import jakarta.persistence.*;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @CreatedDate
    private LocalDateTime createdAt;
}
