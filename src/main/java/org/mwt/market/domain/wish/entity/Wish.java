package org.mwt.market.domain.wish.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Wish(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public String getProductTitle() {
        return product.getTitle();
    }

    public Integer getProductPrice() {
        return product.getPrice();
    }

    public String getProductThumbnail() {
        if (product.getProductAlbum() == null || product.getProductAlbum().isEmpty()) {
            return "https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png";
        }
        return product.getProductAlbum().get(0).getUrl();
    }

    public String getProductStatus() {
        return product.getStatus().getValue();
    }

    public Integer getProductLikes() {
        return product.getLikes();
    }

    public Long getProductId() {
        return product.getProductId();
    }
}
