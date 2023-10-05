package org.mwt.market.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mwt.market.domain.user.dto.UserRequests;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    private String tel;

    private String nickname;

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
