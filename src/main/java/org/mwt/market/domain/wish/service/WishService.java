package org.mwt.market.domain.wish.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.product.service.ProductService;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.user.service.UserService;
import org.mwt.market.domain.wish.dto.WishResponseDto;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.exception.AlreadyExistWishException;
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
        Product product = productRepository.findById(productId).orElseThrow(NoSuchProductException::new);

        if (wishRepository.existsByUserAndProduct(user, product)) {
            throw new AlreadyExistWishException();
        }

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);
    }

    public List<WishResponseDto> getMyWishes(UserPrincipal userPrincipal) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(NoSuchUserException::new);
        List<Wish> wishes = wishRepository.findByUser(user);

        return wishes.stream()
            .map(WishResponseDto::fromEntity)
            .toList();
    }
}
