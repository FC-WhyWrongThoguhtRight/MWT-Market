package org.mwt.market.domain.product.repository;

import java.util.List;
import org.mwt.market.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory_CategoryIdIn(Pageable pageable, List<Long> categoryIds);

    Page<Product> findAllByTitleContaining(Pageable pageable, String searchWord);

    Page<Product> findAllByOrderByProductIdDesc(Pageable pageable);

    @Query("SELECT p FROM Product p "
        + "WHERE p.category.categoryId IN :categoryIds "
        + "AND p.title like %:searchWord% "
        + "ORDER BY p.productId DESC")
    Page<Product> findAllByCategory_CategoryIdInTitleContainingOrderByProductIdDesc(
        Pageable pageable, List<Long> categoryIds,
        String searchWord);

    Page<Product> findBySeller_UserId(Pageable pageable, Long userId);

    @Query("SELECT p FROM Product p "
        + "WHERE p.seller.userId = :sellerId "
        + "AND p.productId <> :productId "
        + "AND p.isDeleted = false "
        + "ORDER BY p.createdAt DESC")
    List<Product> findProductsBySeller_UserId(@Param("sellerId") Long sellerId, @Param("productId") Long productId);
}