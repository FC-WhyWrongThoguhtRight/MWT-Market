package org.mwt.market.domain.wish.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.wish.dto.WishResponseDto;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.exception.AlreadyExistWishException;
import org.mwt.market.domain.wish.exception.NoSuchWishException;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addWish(UserPrincipal userPrincipal, Long productId) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(NoSuchUserException::new);
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));

        if (wishRepository.existsByUserAndProduct(user, product)) {
            throw new AlreadyExistWishException();
        }

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        product.plusLikes();
        productRepository.save(product);
    }

    public List<WishResponseDto> getWishes(UserPrincipal userPrincipal) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(NoSuchUserException::new);
        List<Wish> wishes = wishRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
            .filter(wish -> filteringIsDeleted(wish.getProduct()))
            .toList();

        return wishes.stream()
            .map(WishResponseDto::fromEntity)
            .toList();
    }

    public void removeWish(UserPrincipal userPrincipal, Long productId) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(NoSuchUserException::new);
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));

        Wish wish = wishRepository.findByUserAndProduct(user, product).orElseThrow(NoSuchWishException::new);
        wishRepository.delete(wish);

        product.minusLikes();
        productRepository.save(product);
    }

    private Product validateIsDeleted(Product product) {
        if (product.isDeleted()) {
            throw new NoSuchProductException();
        }
        return product;
    }

    private boolean filteringIsDeleted(Product product) {
        return !product.isDeleted();
    }
}
