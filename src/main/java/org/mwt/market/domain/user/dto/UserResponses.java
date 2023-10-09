package org.mwt.market.domain.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class UserResponses {

    @Getter
    public static class SignupResponseDto {

        private final Long id;
        private final String email;
        private final String phone;
        private final String nickname;

        @Builder
        public SignupResponseDto(Long id, String email,
            String phone, String nickname) {
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.nickname = nickname;
        }
    }

    @Getter
    public static class LoginResponseDto {

        private final String jwtToken;

        @Builder
        public LoginResponseDto(String jwtToken) {
            this.jwtToken = jwtToken;
        }
    }

    @Getter
    public static class UserInfoResponseDto {

        private final String email;
        private final String nickname;
        private final String tel;
        private final String profileImage;

        @Builder
        public UserInfoResponseDto(String email, String nickname, String tel, String profileImage) {
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
    public static class MyProductResponseDto {

        private final List<ProductDto> productDtoList;

        @Builder
        public MyProductResponseDto(List<ProductDto> productDtoList) {
            this.productDtoList = productDtoList;
        }
    }

    @Getter
    public static class MyInterestResponseDto {

        private final List<ProductDto> productDtoList;

        @Builder
        public MyInterestResponseDto(List<ProductDto> productDtoList) {
            this.productDtoList = productDtoList;
        }
    }

    @Getter
    public static class MyChatRoomResponseDto {

        private final List<ChatRoomDto> chatRoomDtoList;

        @Builder
        public MyChatRoomResponseDto(List<ChatRoomDto> chatRoomDtoList) {
            this.chatRoomDtoList = chatRoomDtoList;
        }
    }

    @Getter
    public static class ProductDto {

        private final Long id;
        private final String title;
        private final String price;
        private final String thumbnailImage;
        private final String status;
        private final String likes;

        @Builder
        public ProductDto(Long id, String title, String price, String thumbnailImage,
            String status, String likes) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.thumbnailImage = thumbnailImage;
            this.status = status;
            this.likes = likes;
        }
    }

    @Getter
    public static class ChatRoomDto {

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
        public ChatRoomDto(Long chatRoomId, Long productId, String productImage,
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
    }
}
