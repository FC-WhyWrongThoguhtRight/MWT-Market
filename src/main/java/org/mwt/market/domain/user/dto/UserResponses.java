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
}
