package org.mwt.market.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

public class UserResponseDto {
    @Getter
    public static class SignupResponseDto extends BaseResponseBody {
        private final String message;

        @Builder
        public SignupResponseDto(Integer statusCode, String message) {
            super(statusCode);
            this.message = message;
        }
    }
}
