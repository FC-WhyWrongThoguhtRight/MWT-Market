package org.mwt.market.domain.chat.entity;

import jakarta.persistence.*;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @CreatedDate
    private LocalDateTime createdAt;
}
