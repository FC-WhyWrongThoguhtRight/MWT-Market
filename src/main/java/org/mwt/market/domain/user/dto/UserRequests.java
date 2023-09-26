package org.mwt.market.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequests {
    @Getter
    @NoArgsConstructor
    public static class SignupRequestDto {
        @NotBlank
        @Schema(description = "사용자 이메일", example = "donghar@naver.com")
        private String email;
        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "1q2w3e")
        private String password;
        @NotBlank
        @Schema(description = "사용자 전화번호", example = "010-4708-3628")
        private String phone;
        @NotBlank
        @Schema(description = "사용자 닉네임", example = "myNickname")
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
        @NotBlank
        @Schema(description = "사용자 닉네임", example = "donghar")
        private String nickname;
        @Schema(description = "사용자 프로필", example = "ab3wnwer34aefr")
        private String profile_image;
    }
}
