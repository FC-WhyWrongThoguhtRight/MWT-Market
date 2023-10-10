package org.mwt.market.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String url;

    @CreatedDate
    private LocalDateTime createdAt;

    public static ProfileImage createDefault() {
        ProfileImage newProfileImage = new ProfileImage();
        newProfileImage.update("https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png");
        return newProfileImage;
    }

    public void update(String url) {
        this.url = url;
    }
}
