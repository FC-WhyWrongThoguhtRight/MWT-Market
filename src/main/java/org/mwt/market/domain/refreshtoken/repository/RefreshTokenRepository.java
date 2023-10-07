package org.mwt.market.domain.refreshtoken.repository;

import java.util.Optional;
import org.mwt.market.domain.refreshtoken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByClientIpAndUserAgent(String clientIp, String userAgent);
}
