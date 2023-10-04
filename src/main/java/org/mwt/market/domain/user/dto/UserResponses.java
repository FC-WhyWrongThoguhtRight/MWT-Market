package org.mwt.market.domain.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

public class UserResponses {

    @Getter
    public static class SignupResponseDto extends BaseResponseBody {

        private final String id;
        private final String email;
        private final String phone;
        private final String nickname;

        @Builder
        public SignupResponseDto(Integer statusCode, String message, String id, String email,
            String phone, String nickname) {
            super(statusCode, message);
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.nickname = nickname;
        }
    }

    @Getter
    public static class LoginResponseDto extends BaseResponseBody {

        private final String jwtToken;
        private final UserInfo userInfo;

        @Builder
        public LoginResponseDto(Integer statusCode, String message, String jwtToken,
            UserInfo userInfo) {
            super(statusCode, message);
            this.jwtToken = jwtToken;
            this.userInfo = userInfo;
        }
    }

    @Getter
    public static class ProfileUpdateResponseDto extends BaseResponseBody {

        private final String id;
        private final String email;
        private final String phone;
        private final String nickname;

        @Builder
        public ProfileUpdateResponseDto(Integer statusCode, String message, String id, String email,
            String phone, String nickname) {
            super(statusCode, message);
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.nickname = nickname;
        }
    }

    @Getter
    public static class MyInterestResponseDto extends BaseResponseBody {

        private final List<ProductDto> productDtoList;

        @Builder
        public MyInterestResponseDto(Integer statusCode, String message,
            List<ProductDto> productDtoList) {
            super(statusCode, message);
            this.productDtoList = productDtoList;
        }
    }

    @Getter
    public static class MyChatRoomResponseDto extends BaseResponseBody {

        private final List<ChatRoomDto> chatRoomDtoList;

        @Builder
        public MyChatRoomResponseDto(Integer statusCode, String message,
            List<ChatRoomDto> chatRoomDtoList) {
            super(statusCode, message);
            this.chatRoomDtoList = chatRoomDtoList;
        }
    }

    @Getter
    public static class UserInfo {

        private final String id;
        private final String email;
        private final String name;
        private final String phone;
        private final String nickname;

        @Builder
        public UserInfo(String id, String email, String name, String phone, String nickname) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.phone = phone;
            this.nickname = nickname;
        }
    }

    @Getter
    public static class ProductDto {

        private final String id;
        private final String title;
        private final String price;
        private final String thumbnailImage;
        private final String status;
        private final String likes;

        @Builder
        public ProductDto(String id, String title, String price, String thumbnailImage,
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

        private final String chatRoomId;
        private final String productId;
        private final String productImage;
        private final String productStatus;
        private final String personId;
        private final String personNickname;
        private final String personProfileImage;
        private final String lastMessage;
        private final String lastChattedAt;

        @Builder
        public ChatRoomDto(String chatRoomId, String productId, String productImage,
            String productStatus, String personId, String personNickname, String personProfileImage,
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
