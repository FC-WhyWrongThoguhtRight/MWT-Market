package org.mwt.market.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mwt.market.common.util.jwt.JwtProvider;
import org.mwt.market.config.security.token.AjaxAuthenticationToken;
import org.mwt.market.domain.refreshtoken.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProvider jwtProvider;

    public AjaxAuthenticationSuccessHandler(JwtProvider jwtProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // access-token
        String accessToken = jwtProvider.generateAccessToken((AjaxAuthenticationToken) authentication);
        objectMapper.writeValue(response.getWriter(), """
                {"accessToken": %s}""".formatted(accessToken));

        // TODO: refresh-token
    }
}
