package org.mwt.market.config.security.token;

import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * AuthenticationPrincipalArgumentResolver가 바인딩한 결과로 뱉어주는 객체
 * 1.Jwt에 들어갈 데이터이므로 민감한 정보는 배제할 것.
 * 2.Controller에서 필요한 데이터를 필드로 추가할 수 있다.
 */
public class UserPrincipal implements AuthenticatedPrincipal {
    private final Long userId;
    private final String email;

    public UserPrincipal(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
