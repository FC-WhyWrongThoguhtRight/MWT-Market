package org.mwt.market.domain.product.entity;

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
import lombok.Builder;
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
    private Long productId;
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

    @Builder
    public Product(String title, String content, Integer price, List<ProductImage> productAlbum,
        User seller, ProductCategory productCategory) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.productAlbum = productAlbum;
        this.seller = seller;
        this.productCategory = productCategory;
        this.status = ProductStatus.TRADE;
        this.isDeleted = false;
        this.likes = 0;
    }

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
  
    public void update(String title, String content, ProductCategory category, Integer price, List<ProductImage> productAlbum) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.productCategory = category;
        this.productAlbum = productAlbum;
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

    public void setProductAlbum(List<ProductImage> productAlbum) {
        this.productAlbum = productAlbum;
    }
}
