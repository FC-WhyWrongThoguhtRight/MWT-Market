package org.mwt.market.config.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import org.mwt.market.config.security.exception.InvalidJwtException;
import org.mwt.market.config.security.service.RefreshTokenService;
import org.mwt.market.config.security.token.AuthenticationDetails;
import org.mwt.market.config.security.token.JwtAuthenticationToken;
import org.mwt.market.config.security.token.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements AuthenticationProvider {

    private final String issuer;
    private long accessTokenValidityInMs;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    private final RefreshTokenService refreshTokenService;
    private final long refreshTokenValidityInMs;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.issuer}") String issuer,
        @Value("${jwt.access-token-expire-time}") long accessTokenValidityInMs,
        RefreshTokenService refreshTokenService,
        @Value("${jwt.refresh-token-expire-time}") long refreshTokenValidityInMs) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.issuer = issuer;
        this.accessTokenValidityInMs = accessTokenValidityInMs;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenValidityInMs = refreshTokenValidityInMs;
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String generateAccessToken(Authentication authToken) {
        LocalDateTime now = LocalDateTime.now();
        return JWT.create()
            .withSubject(authToken.getName())
            .withClaim("authn", objectMapper.convertValue(authToken.getPrincipal(),
                new TypeReference<Map<String, Object>>() {
                }))
            .withArrayClaim("authgr",
                authToken.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .toArray(String[]::new))
            .withIssuer(issuer)
            .withIssuedAt(now.atZone(ZoneId.systemDefault()).toInstant())
            .withExpiresAt(
                now.plus(accessTokenValidityInMs, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault())
                    .toInstant())
            .sign(algorithm);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String accessTokenValue = (String) authentication.getPrincipal();
        String refreshTokenValue = (String) authentication.getCredentials();
        JwtAuthenticationToken authResult = null;
        try {
            DecodedJWT decodedJWT = verifier.verify(accessTokenValue);
            UserPrincipal userPrincipal = decodedJWT.getClaim("authn").as(UserPrincipal.class);
            List<GrantedAuthority> authorities = decodedJWT.getClaim("authgr")
                .asList(GrantedAuthority.class);
            authResult = JwtAuthenticationToken.authenticated(userPrincipal, null, authorities);
        } catch (TokenExpiredException ex) {
            DecodedJWT decodedJWT = JWT.decode(accessTokenValue);
            UserPrincipal userPrincipal = decodedJWT.getClaim("authn").as(UserPrincipal.class);
            List<GrantedAuthority> authorities = decodedJWT.getClaim("authgr")
                .asList(GrantedAuthority.class);
            AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
            boolean isValidRefreshToken = refreshTokenService.isValidRefreshToken(refreshTokenValue,
                userPrincipal, details);
            if (isValidRefreshToken) {
                authResult = JwtAuthenticationToken.authenticated(
                    userPrincipal, null, authorities);
                String newAccessToken = generateAccessToken(authResult);
                String newRefreshToken = refreshTokenService.updateRefreshToken(userPrincipal,
                    details);
                authResult = JwtAuthenticationToken.authenticated(
                    userPrincipal, null, authorities, newAccessToken, newRefreshToken);
            }
        } catch (JWTVerificationException ex) {
            throw new InvalidJwtException(ex.getMessage(), ex);
        }
        authResult.setDetails(authentication.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
