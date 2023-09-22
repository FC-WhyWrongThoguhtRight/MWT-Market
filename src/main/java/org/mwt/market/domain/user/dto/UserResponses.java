package org.mwt.market.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.user.entity.User;

public class UserResponses {
    @Getter
    public static class SignupResponseDto extends BaseResponseBody {
        private final User user;

        @Builder
        public SignupResponseDto(Integer statusCode, String message, User user) {
            super(statusCode, message);
            this.user = user;
        }
    }

    @Getter
    public static class LoginResponseDto extends BaseResponseBody {
        private final User user;

        @Builder
        public LoginResponseDto(Integer statusCode, String message, User user) {
            super(statusCode, message);
            this.user = user;
        }
    }

    @Getter
    public static class ProfileUpdateResponseDto extends BaseResponseBody {
        private final User user;

        @Builder
        public ProfileUpdateResponseDto(Integer statusCode, String message, User user) {
            super(statusCode, message);
            this.user = user;
        }
    }

    @Getter
    public static class MyInterestResponseDto extends BaseResponseBody {
        private final User user;

        @Builder
        public MyInterestResponseDto(Integer statusCode, String message, User user) {
            super(statusCode, message);
            this.user = user;
        }
    }

    @Getter
    public static class MyChatRoomResponseDto extends BaseResponseBody {
        private final User user;

        @Builder
        public MyChatRoomResponseDto(Integer statusCode, String message, User user) {
            super(statusCode, message);
            this.user = user;
        }
    }
}
