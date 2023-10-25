package org.mwt.market.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class UserRequests {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupRequestDto {

        @Email
        @Schema(description = "사용자 이메일", example = "donghar@naver.com")
        private String email;
        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "1q2w3e")
        private String password;
        @Pattern(regexp = "^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})+$")
        @Schema(description = "사용자 전화번호", example = "010-4708-3628")
        private String phone;
        @Size(min = 1, max = 10)
        @Schema(description = "사용자 닉네임", example = "myNickname")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDto {

        @Email
        @Schema(description = "사용자 이메일", example = "donghar@naver.com")
        private String email;
        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "1q2w3e")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileUpdateRequestDto {

        @Schema(description = "사용자 닉네임", example = "donghar")
        private String nickname;
        @Schema(description = "이미지 바이너리 데이터")
        private MultipartFile profileImg;
    }
}
