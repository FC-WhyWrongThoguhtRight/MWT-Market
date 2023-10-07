package org.mwt.market.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.util.cookie.CookieUtil;
import org.mwt.market.common.util.jwt.JwtProvider;
import org.mwt.market.config.security.token.AuthenticationDetails;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.refreshtoken.entity.RefreshToken;
import org.mwt.market.domain.refreshtoken.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AjaxAuthenticationSuccessHandler(JwtProvider jwtProvider,
        RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        // access-token
        String accessToken = jwtProvider.generateAccessToken(authentication);

        CookieUtil.addCookie(response, "access-token", accessToken, 60 * 24);

        // refresh-token
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        String refreshTokenValue = UUID.randomUUID().toString();

        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
        String clientIp = details.getClientIp();
        String userAgent = details.getUserAgent();
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByClientIpAndUserAgent(
            clientIp, userAgent);
        if (refreshToken.isPresent()) {
            refreshToken.get().update(userId, refreshTokenValue);
            refreshTokenRepository.saveAndFlush(refreshToken.get());
        } else {
            refreshTokenRepository.saveAndFlush(
                RefreshToken.create(userId, refreshTokenValue, clientIp, userAgent));
        }

        CookieUtil.addCookie(response, "refresh-token", refreshTokenValue, 60 * 60 * 24);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), BaseResponseBody.success("login success"));
    }
}
