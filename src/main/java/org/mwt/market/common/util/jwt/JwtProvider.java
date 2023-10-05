package org.mwt.market.common.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.mwt.market.config.security.token.AjaxAuthenticationToken;
import org.mwt.market.config.security.token.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
public class JwtProvider {
    private static final String HEADER_TOKEN_PREFIX = "Bearer ";
    private final String issuer;
    private final long accessTokenValidityInMs;
    private final long refreshTokenValidityInMs;

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.issuer}") String issuer, @Value("${jwt.access-token-expire-time}") long accessTokenValidityInMs, @Value("${jwt.refresh-token-expire-time}") long refreshTokenValidityInMs) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.issuer = issuer;
        this.accessTokenValidityInMs = accessTokenValidityInMs;
        this.refreshTokenValidityInMs = refreshTokenValidityInMs;
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String generateAccessToken(AjaxAuthenticationToken authenticationToken) {
        LocalDateTime now = LocalDateTime.now();
        return JWT.create()
                .withSubject(authenticationToken.getName())
                .withClaim("authn", objectMapper.convertValue(authenticationToken.getPrincipal(), new TypeReference<Map<String, Object>>() {
                }))
                .withArrayClaim("authgr", authenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withIssuer(issuer)
                .withIssuedAt(now.atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(now.plus(accessTokenValidityInMs, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant())
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(HEADER_TOKEN_PREFIX)) {
            return bearerToken.substring(HEADER_TOKEN_PREFIX.length());
        }
        return null;
    }

    public AjaxAuthenticationToken getAjaxAuthentication(String accessToken) {
        DecodedJWT decodedJWT = verifier.verify(accessToken);
        UserPrincipal userPrincipal = decodedJWT.getClaim("authn").as(UserPrincipal.class);
        List<GrantedAuthority> authorities = decodedJWT.getClaim("authgr").asList(GrantedAuthority.class);
        return AjaxAuthenticationToken.authenticated(userPrincipal, null, authorities);
    }
}
