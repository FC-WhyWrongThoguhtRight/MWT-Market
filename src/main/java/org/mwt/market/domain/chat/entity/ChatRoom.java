package org.mwt.market.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mwt.market.domain.chat.dto.ChatRoomRequestDto;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.user.dto.UserRequests;
import org.mwt.market.domain.user.entity.ProfileImage;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private Long buyerId;

    private Long productId;

    @CreatedDate
    private LocalDateTime createdAt;

    public ChatRoom(Long userId, Long productId) {
        this.buyerId = userId;
        this.productId = productId;
    }

    public static ChatRoom createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        return new ChatRoom(
            chatRoomRequestDto.getUserId(),
            chatRoomRequestDto.getProductId()
        );
    }

}
