package org.mwt.market.config.security.provider;

import org.mwt.market.config.security.service.AjaxUserDetailService;
import org.mwt.market.config.security.service.UserDetailsWithId;
import org.mwt.market.config.security.token.AjaxAuthenticationToken;
import org.mwt.market.config.security.token.UserPrincipal;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final AjaxUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public AjaxAuthenticationProvider(AjaxUserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetailsWithId userDetails = (UserDetailsWithId) userDetailService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }
        UserPrincipal userPrincipal = new UserPrincipal(userDetails.getUserId(), userDetails.getUsername());
        AjaxAuthenticationToken result = AjaxAuthenticationToken.authenticated(userPrincipal, null, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
