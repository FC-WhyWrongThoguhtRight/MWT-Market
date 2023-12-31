package org.mwt.market.domain.user.repository;

import java.util.Optional;
import org.mwt.market.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByClientIpAndUserAgent(String clientIp, String userAgent);

    Optional<RefreshToken> findByTokenValue(String tokenValue);
}
