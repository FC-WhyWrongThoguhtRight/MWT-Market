package org.mwt.market.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.util.cookie.CookieUtil;
import org.mwt.market.config.security.provider.JwtProvider;
import org.mwt.market.config.security.service.RefreshTokenService;
import org.mwt.market.config.security.token.AuthenticationDetails;
import org.mwt.market.config.security.token.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AjaxAuthenticationSuccessHandler(JwtProvider jwtProvider,
        RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        // access-token
        String accessToken = jwtProvider.generateAccessToken(authentication);

        CookieUtil.addCookie(response, "access-token", accessToken, 60 * 30);

        // refresh-token
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
        String refreshTokenValue = refreshTokenService.updateRefreshToken(principal, details);

        CookieUtil.addCookie(response, "refresh-token", refreshTokenValue, 60 * 60 * 24);

        // response
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), BaseResponseBody.success("login success"));
    }
}
