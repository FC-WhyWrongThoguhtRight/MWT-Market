package org.mwt.market.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.product.entity.Product;

public class UserResponses {

    @Getter
    public static class UserInfoResponseDto {

        private final Long id;
        private final String email;
        private final String nickname;
        private final String tel;
        private final String profileImage;

        @Builder
        public UserInfoResponseDto(Long id, String email, String nickname, String tel, String profileImage) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
            this.tel = tel;
            this.profileImage = profileImage;
        }
    }

    @Getter
    public static class ProfileUpdateResponseDto {

        private final Long id;
        private final String email;
        private final String phone;
        private final String nickname;
        private final String profileImg;

        @Builder
        public ProfileUpdateResponseDto(Long id, String email, String phone, String nickname,
            String profileImg) {
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.nickname = nickname;
            this.profileImg = profileImg;
        }
    }

    @Getter
    public static class ProductDto {

        private final Long id;
        private final String title;
        private final Integer price;
        private final String thumbnail;
        private final String status;
        private final Integer likes;

        @Builder
        public ProductDto(Long id, String title, Integer price, String thumbnail,
            String status, Integer likes) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.thumbnail = thumbnail;
            this.status = status;
            this.likes = likes;
        }

        public static ProductDto fromEntity(Product product) {
            return ProductDto.builder()
                .id(product.getProductId())
                .title(product.getTitle())
                .price(product.getPrice())
                .status(product.getStatus().getValue())
                .likes(product.getLikes())
                .thumbnail(product.getThumbnail())
                .build();
        }
    }

    @Getter
    public static class UserChatRoomDto {

        private final Long chatRoomId;
        private final Long productId;
        private final String productImage;
        private final String productStatus;
        private final Long personId;
        private final String personNickname;
        private final String personProfileImage;
        private final String lastMessage;
        private final String lastChattedAt;

        @Builder
        public UserChatRoomDto(Long chatRoomId, Long productId, String productImage,
            String productStatus, Long personId, String personNickname, String personProfileImage,
            String lastMessage, String lastChattedAt) {
            this.chatRoomId = chatRoomId;
            this.productId = productId;
            this.productImage = productImage;
            this.productStatus = productStatus;
            this.personId = personId;
            this.personNickname = personNickname;
            this.personProfileImage = personProfileImage;
            this.lastMessage = lastMessage;
            this.lastChattedAt = lastChattedAt;
        }

        public static UserChatRoomDto fromEntity(ChatRoom chatRoom) {
            return UserChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .productId(chatRoom.getProduct().getProductId())
                .productImage(chatRoom.getProduct().getThumbnail())
                .productStatus(chatRoom.getProduct().getStatus().getValue())
                //TODO: 내 채팅방 ResponseDto
//                .personId(chatRoom.getBuyer().getUserId())
//                .personNickname()
//                .personProfileImage()
//                .lastMessage()
//                .lastChattedAt()
                .build();
        }
    }
}
