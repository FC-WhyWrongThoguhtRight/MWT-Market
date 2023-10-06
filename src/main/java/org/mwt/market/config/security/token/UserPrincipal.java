package org.mwt.market.config.security.token;

import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * AuthenticationPrincipalArgumentResolver가 바인딩한 결과로 뱉어주는 객체
 */
public class UserPrincipal implements AuthenticatedPrincipal {

    private final Long id;
    private final String email;

    public UserPrincipal(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
