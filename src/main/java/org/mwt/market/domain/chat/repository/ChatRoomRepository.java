package org.mwt.market.domain.chat.repository;

import java.util.List;
import java.util.Optional;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.entity.ChatUserVo;
import org.mwt.market.domain.product.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomId(Long ChatRoomId);

    Optional<ChatRoom> findByBuyer_UserIdAndProduct_ProductId(Long userId, Long productId);

    List<ChatRoom> findAllByProduct(Product product);

    //TODO: 쿼리 개선방안
//    @Query("select c from ChatRoom c join fetch c.product p left join fetch p.productAlbum a where c.buyer.userId = :buyerId or p.seller.userId = :sellerId")
    List<ChatRoom> findByBuyer_UserIdOrProduct_Seller_UserId(PageRequest of, Long buyerId,
        Long sellerId);

    @Query(value =
        "SELECT a.chatRoomId, a.userId as userId1, a.nickName as nickName1, a.url as url1, "
            + "b.userId as userId2, b.nickName as nickName2, b.url as url2 "
            + "FROM (   SELECT c.chatRoomId, u.userId , u.nickName, c.product_productId, pi.url "
            + "         FROM ChatRoom c "
            + "         JOIN users u ON c.buyer_userId = u.userId "
            + "         JOIN ProfileImage pi ON u.profile_img = pi.profileid) as a "
            + ", (  SELECT p.productId, u.userId, u.nickName, pi.url "
            + "         FROM Product p "
            + "         JOIN users u ON p.sellerId = u.userId "
            + "         JOIN ProfileImage pi ON u.profile_img = pi.profileid) as b "
            + "WHERE a.product_productId = b.productid "
            + "AND chatroomid = :chatRoomId ", nativeQuery = true)
    ChatUserVo findChatroomUsers(@Param("chatRoomId") Long chatRoomId);
}
