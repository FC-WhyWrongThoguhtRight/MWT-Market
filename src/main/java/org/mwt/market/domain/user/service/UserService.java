package org.mwt.market.domain.user.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.user.dto.UserRequests.ProfileUpdateRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.mwt.market.domain.user.entity.ProfileImage;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.exception.UserRegisterException;
import org.mwt.market.domain.user.exception.UserUpdateException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Template s3Template;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        S3Template s3Template) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Template = s3Template;
    }

    @Transactional
    public User registerUser(SignupRequestDto signupRequestDto)
        throws UserRegisterException {
        ProfileImage defaultProfile = ProfileImage.createDefault();
        User newUser = User.createNewUser(signupRequestDto, passwordEncoder, defaultProfile);
        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            throw new UserRegisterException("이미 가입된 이메일 입니다.", ex);
        }
        return newUser;
    }

    public User readUser(UserPrincipal userPrincipal) throws NoSuchUserException {
        User findUser = userRepository.findById(userPrincipal.getId())
            .orElseThrow(() -> new NoSuchUserException());
        return findUser;
    }

    @Transactional
    public User updateUser(UserPrincipal userPrincipal,
        ProfileUpdateRequestDto profileUpdateRequestDto)
        throws UserUpdateException {
        String newNickname = profileUpdateRequestDto.getNickname();
        MultipartFile newProfileImage = profileUpdateRequestDto.getProfileImg();
        String extension = StringUtils.getFilenameExtension(newProfileImage.getOriginalFilename());
        User currUser = userRepository.findById(userPrincipal.getId())
            .orElseThrow(() -> new UserUpdateException("수정하고자 하는 유저가 존재하지 않습니다."));
        currUser.updateNickname(newNickname);
        try {
            S3Resource resource = s3Template.upload("mwtmarketbucket",
                "user" + "/" + userPrincipal.getId(),
                newProfileImage.getInputStream(),
                ObjectMetadata.builder().contentType(extension).build());
            currUser.getProfileImage().update(resource.getURL().toString());
        } catch (IOException ex) {
            throw new UserUpdateException("파일 에러 발생", ex);
        }
        return currUser;
    }
}
