package org.mwt.market.domain.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.product.dto.ProductInfoDto;
import org.mwt.market.domain.product.dto.ProductSearchRequestDto;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.exception.AlreadyGoneException;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;

    public List<ProductInfoDto> findAllProducts(ProductSearchRequestDto request, UserPrincipal userPrincipal) {

        List<Integer> categoryIds = request.getCategoryIds();
        String searchWord = request.getSearchWord();
        Integer pageSize = request.getPageSize();
        Integer page = request.getPage();

        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize, Sort.sort(Product.class).descending()));

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NoSuchUserException::new);
        List<Wish> findWishProducts = wishRepository.findAllByUser(user);
        Set<Long> wishProductIds = findWishProducts.stream()
                .map(wish -> wish.getProduct().getId())
                .collect(Collectors.toSet());

        List<ProductInfoDto> productInfos = products.stream()
                .map(ProductInfoDto::toDto)
                .collect(Collectors.toList());

        return productInfos;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(AlreadyGoneException::new);
        product.delete();
    }
}
