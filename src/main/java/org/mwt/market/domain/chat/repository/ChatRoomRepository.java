package org.mwt.market.domain.chat.repository;

import java.util.Optional;
import java.util.List;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.product.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomId(Long ChatRoomId);

    Optional<ChatRoom> findByBuyer_UserIdAndProduct_ProductId(Long userId, Long productId);

    List<ChatRoom> findAllByProduct(Product product);

    //TODO: 쿼리 개선방안
//    @Query("select c from ChatRoom c join fetch c.product p left join fetch p.productAlbum a where c.buyer.userId = :buyerId or p.seller.userId = :sellerId")
    List<ChatRoom> findByBuyer_UserIdOrProduct_Seller_UserId(PageRequest of, Long buyerId, Long sellerId);
}
