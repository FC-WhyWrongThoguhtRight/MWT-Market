package org.mwt.market.domain.product.repository;

import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);

    Page<Product> findBySeller_UserId(Pageable pageable, Long userId);
}
