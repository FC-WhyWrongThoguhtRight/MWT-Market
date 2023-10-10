package org.mwt.market.domain.wish.repository;

import java.util.List;
import java.util.Optional;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    boolean existsByUserAndProduct(User user, Product product);

    List<Wish> findByUser(User user);

    Optional<Wish> findByUserAndProduct(User user, Product product);
}
