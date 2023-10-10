package org.mwt.market.domain.wish.service;

import lombok.RequiredArgsConstructor;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.service.ProductService;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.service.UserService;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.exception.AlreadyExistWishException;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishService {

    private final UserService userService;
    private final ProductService productService;

    private final WishRepository wishRepository;

    public void addWish(UserPrincipal userPrincipal, Long productId) {
        User user = userService.readUser(userPrincipal);
        Product product = productService.getProduct(productId);

        if (wishRepository.existsByUserAndProduct(user, product)) {
            throw new AlreadyExistWishException();
        }

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);
    }
}
