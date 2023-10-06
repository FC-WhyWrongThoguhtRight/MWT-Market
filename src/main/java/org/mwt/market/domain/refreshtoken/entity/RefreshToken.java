package org.mwt.market.domain.refreshtoken.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = @Index(columnList = "tokenValue"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(unique = true)
    private String tokenValue;

    private String ipAdd;

    private String userAgent;

    public RefreshToken(Long userId, String tokenValue, String ipAdd, String userAgent) {
        this.userId = userId;
        this.tokenValue = tokenValue;
        this.ipAdd = ipAdd;
        this.userAgent = userAgent;
    }

    public void update(Long userId, String tokenValue, String ipAdd, String userAgent) {
        this.userId = userId;
        this.tokenValue = tokenValue;
        this.ipAdd = ipAdd;
        this.userAgent = userAgent;
    }

    public static RefreshToken create(Long userId, String tokenValue, String ipAdd, String userAgent) {
        return new RefreshToken(userId, tokenValue, ipAdd, userAgent);
    }
}
