package org.mwt.market.domain.chat.repository;

import java.util.Optional;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomId(Long ChatRoomId);

    Optional<ChatRoom> findByBuyerIdAndProductId(Long buyerId, Long ChatRoomId);

}
