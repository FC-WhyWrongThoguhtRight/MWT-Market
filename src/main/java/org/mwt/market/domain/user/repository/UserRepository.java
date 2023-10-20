package org.mwt.market.domain.user.repository;

import java.util.List;
import java.util.Optional;
import org.mwt.market.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByEmailOrNicknameOrTel(String email, String nickname, String phone);

    Optional<User> findByNickname(String nickname);
}
