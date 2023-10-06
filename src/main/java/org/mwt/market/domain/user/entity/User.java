package org.mwt.market.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mwt.market.domain.user.dto.UserRequests;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;

    private String password;

    private String tel;

    private String nickname;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_img")
    private ProfileImage profileImage;

    @CreatedDate
    private LocalDateTime createdAt;

    public User(String email, String password, String tel, String nickname) {
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.nickname = nickname;
    }

    public static User create(UserRequests.SignupRequestDto signupRequestDto, PasswordEncoder passwordEncoder) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String tel = signupRequestDto.getPhone();
        String nickname = signupRequestDto.getNickname();
        return new User(email, password, tel, nickname);
    }
}
