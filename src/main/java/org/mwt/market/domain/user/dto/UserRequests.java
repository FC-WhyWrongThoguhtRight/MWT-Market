package org.mwt.market.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class UserRequests {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
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

        public SignupRequestDto(String email, String password, String phone, String nickname) {
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.nickname = nickname;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDto {

        @NotBlank
        @Schema(description = "사용자 이메일", example = "donghar@naver.com")
        private String email;
        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "1q2w3e")
        private String password;

        public LoginRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileUpdateRequestDto {

        @NotBlank
        @Schema(description = "사용자 닉네임", example = "donghar")
        private String nickname;
        @NotBlank
        @Schema(description = "이미지 바이너리 데이터")
        private MultipartFile profileImg;
    }
}
