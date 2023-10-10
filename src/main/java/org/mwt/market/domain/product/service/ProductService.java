package org.mwt.market.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    public Product getProduct(Long productId) {
        return productRepo.findById(productId).orElseThrow(NoSuchProductException::new);
    }
}
