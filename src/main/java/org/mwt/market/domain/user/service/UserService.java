package org.mwt.market.domain.user.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.dto.UserRequests.ProfileUpdateRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.mwt.market.domain.user.dto.UserResponses.ChatRoomDto;
import org.mwt.market.domain.user.dto.UserResponses.ProductDto;
import org.mwt.market.domain.user.entity.ProfileImage;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.DuplicateEmailException;
import org.mwt.market.domain.user.exception.DuplicateNicknameException;
import org.mwt.market.domain.user.exception.DuplicatePhoneException;
import org.mwt.market.domain.user.exception.DuplicateValueException;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.exception.UserUpdateException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Template s3Template;


    public UserService(UserRepository userRepository, ProductRepository productRepository,
        ChatRoomRepository chatRoomRepository, PasswordEncoder passwordEncoder,
        S3Template s3Template) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Template = s3Template;
    }

    @Transactional
    public User registerUser(SignupRequestDto signupRequestDto) {
        ProfileImage defaultProfile = ProfileImage.createDefault();
        User newUser = User.createNewUser(signupRequestDto, passwordEncoder, defaultProfile);
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    public boolean isDuplicated(SignupRequestDto signupRequestDto) throws DuplicateValueException {
        String requestEmail = signupRequestDto.getEmail();
        String requestNickname = signupRequestDto.getNickname();
        String requestTel = signupRequestDto.getPhone();

        List<User> byEmailOrNicknameOrTel = userRepository.findByEmailOrNicknameOrTel(
            requestEmail, requestNickname, requestTel);
        if (!byEmailOrNicknameOrTel.isEmpty()) {
            if (byEmailOrNicknameOrTel.stream()
                .anyMatch(user -> user.getEmail().equals(requestEmail))) {
                throw new DuplicateEmailException();
            }
            if (byEmailOrNicknameOrTel.stream()
                .anyMatch(user -> user.getTel().equals(requestTel))) {
                throw new DuplicatePhoneException();
            }
            if (byEmailOrNicknameOrTel.stream()
                .anyMatch(user -> user.getNickname().equals(requestNickname))) {
                throw new DuplicateNicknameException();
            }
        }
        return false;
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

    public List<ProductDto> getMyProduct(Integer page, Integer pageSize,
        UserPrincipal userPrincipal) {

        Page<Product> myProductList = productRepository.findBySeller_UserId(
            PageRequest.of(page, pageSize), userPrincipal.getId());

        return myProductList.stream()
            .map(ProductDto::fromEntity)
            .collect(Collectors.toList());
    }

    public List<ChatRoomDto> getMyChatRoom(Integer page, Integer pageSize,
        UserPrincipal userPrincipal) {

        List<ChatRoom> myChatRoomList = chatRoomRepository.findByBuyer_UserIdOrProduct_Seller_UserId(
            PageRequest.of(page, pageSize), userPrincipal.getId(), userPrincipal.getId());

        return myChatRoomList.stream()
            .map(ChatRoomDto::fromEntity)
            .collect(Collectors.toList());
    }
}
