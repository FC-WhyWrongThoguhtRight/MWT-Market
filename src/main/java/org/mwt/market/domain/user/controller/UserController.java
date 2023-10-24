package org.mwt.market.domain.user.controller;

import static org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import static org.mwt.market.domain.user.dto.UserRequests.ProfileUpdateRequestDto;
import static org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import static org.mwt.market.domain.user.dto.UserResponses.ProfileUpdateResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.user.dto.UserResponses.UserChatRoomDto;
import org.mwt.market.domain.user.dto.UserResponses.ProductDto;
import org.mwt.market.domain.user.dto.UserResponses.UserInfoResponseDto;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.UserRegisterException;
import org.mwt.market.domain.user.exception.UserUpdateException;
import org.mwt.market.domain.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public BaseResponseBody signup(
        @Validated @RequestBody SignupRequestDto signupRequestDto) throws UserRegisterException {
        userService.isDuplicated(signupRequestDto);
        userService.registerUser(signupRequestDto);
        return BaseResponseBody.success("회원가입 성공");
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public BaseResponseBody login(
        @Validated @RequestBody LoginRequestDto loginRequestDto) {
        throw new RuntimeException("로그인 기능은 필터에서 처리되어야 합니다.");
    }

    @GetMapping("/myInfo")
    @Operation(summary = "사용자 정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public DataResponseBody<UserInfoResponseDto> info(
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User findUser = userService.readUser(userPrincipal);
        return DataResponseBody.success(
            UserInfoResponseDto.builder()
                .id(findUser.getUserId())
                .email(findUser.getEmail())
                .nickname(findUser.getNickname())
                .tel(findUser.getTel())
                .profileImage(findUser.getProfileImage().getUrl())
                .build()
        );
    }

    @PutMapping(value = "/myPage/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "내 프로필 변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public DataResponseBody<ProfileUpdateResponseDto> updateProfile(
        @Validated @ModelAttribute ProfileUpdateRequestDto profileUpdateRequestDto,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User updatedUser;
        if (StringUtils.hasText(profileUpdateRequestDto.getNickname())
            && !profileUpdateRequestDto.getProfileImg().isEmpty()) {
            updatedUser = userService.updateUser(userPrincipal, profileUpdateRequestDto);
        } else if (StringUtils.hasText(profileUpdateRequestDto.getNickname())) {
            updatedUser = userService.updateNickname(userPrincipal,
                profileUpdateRequestDto.getNickname());
        } else if (!profileUpdateRequestDto.getProfileImg().isEmpty()) {
            updatedUser = userService.updateProfileImg(userPrincipal,
                profileUpdateRequestDto.getProfileImg());
        } else {
            throw new UserUpdateException("값이 입력되지 않았습니다.");
        }
        return DataResponseBody.success(
            ProfileUpdateResponseDto.builder()
                .id(updatedUser.getUserId())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getTel())
                .nickname(updatedUser.getNickname())
                .profileImg(updatedUser.getProfileImage().getUrl())
                .build()
        );
    }

    @GetMapping("/myPage/products")
    @Operation(summary = "내 판매내역 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public DataResponseBody<List<ProductDto>> getMyProduct(
        @Positive @RequestParam(defaultValue = "1") Integer page,
        @Positive @RequestParam(defaultValue = "10") Integer pageSize,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ProductDto> myProductList = userService.getMyProduct(page - 1, pageSize,
            userPrincipal);
        return DataResponseBody.success(myProductList);
    }

    @GetMapping("/myPage/chats")
    @Operation(summary = "나의 채팅방 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    public DataResponseBody<List<UserChatRoomDto>> getMyChatRoom(
        @Positive @RequestParam(defaultValue = "1") Integer page,
        @Positive @RequestParam(defaultValue = "10") Integer pageSize,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<UserChatRoomDto> myUserChatRoomDto = userService.getMyChatRoom(page - 1, pageSize,
            userPrincipal);
        return DataResponseBody.success(myUserChatRoomDto);
    }
}
