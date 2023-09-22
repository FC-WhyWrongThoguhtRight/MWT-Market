package org.mwt.market.domain.user.dto;

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
}
