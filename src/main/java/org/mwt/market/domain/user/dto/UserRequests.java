package org.mwt.market.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequests {
    @Getter
    @NoArgsConstructor
    public static class SignupRequestDto {
        private String email;
        private String password;
        private String name;
        private String phone;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {
        @NotBlank
        @Schema(description = "사용자 이메일", example = "donghar@naver.com")
        private String email;
        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "1q2w3e")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class ProfileUpdateRequestDto {
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class MyInterestRequestDto {
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class MyChatRoomRequestDto {
        private String email;
        private String password;
    }
}
