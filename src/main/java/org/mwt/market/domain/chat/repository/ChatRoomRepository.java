package org.mwt.market.domain.chat.repository;

import java.util.Optional;
import java.util.List;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomId(Long ChatRoomId);

    Optional<ChatRoom> findByBuyer_UserIdAndChatRoomId(Long userId, Long ChatRoomId);

    List<ChatRoom> findAllByProduct(Product product);
}
