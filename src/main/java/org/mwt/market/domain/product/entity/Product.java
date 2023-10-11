package org.mwt.market.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.mwt.market.common.util.BooleanToNumConverter;
import org.mwt.market.common.util.ProductStatusToNumConverter;
import org.mwt.market.domain.product.vo.ProductStatus;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String sellPlace;
    private String title;
    private String content;
    private Integer price;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productAlbum;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;

    @Convert(converter = ProductStatusToNumConverter.class)
    private ProductStatus status;

    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Convert(converter = BooleanToNumConverter.class)
    private boolean isDeleted;

    private LocalDateTime deletedAt;

    private Integer likes;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    
    public void minusLikes() {
        this.likes--;
    }

    public void plusLikes() {
        this.likes++;
    }

    public void changeStatus(String status) {
        this.status = ProductStatus.getFromValue(status);
    }

    public List<String> getImages() {
        return productAlbum.stream().map(ProductImage::getUrl).toList();
    }

    public Long getCategoryId() {
        return this.productCategory.getCategoryId();
    }

    public String getSellerEmail() {
        return seller.getEmail();
    }

    public String getThumbnail() {
        if (productAlbum == null || productAlbum.isEmpty()) {
            return "https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png";
        }
        return productAlbum.get(0).getUrl();
    }
}
