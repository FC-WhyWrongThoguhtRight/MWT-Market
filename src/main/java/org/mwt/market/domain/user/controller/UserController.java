package org.mwt.market.domain.user.controller;

import static org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import static org.mwt.market.domain.user.dto.UserRequests.ProfileUpdateRequestDto;
import static org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import static org.mwt.market.domain.user.dto.UserResponses.LoginResponseDto;
import static org.mwt.market.domain.user.dto.UserResponses.MyChatRoomResponseDto;
import static org.mwt.market.domain.user.dto.UserResponses.MyInterestResponseDto;
import static org.mwt.market.domain.user.dto.UserResponses.ProfileUpdateResponseDto;
import static org.mwt.market.domain.user.dto.UserResponses.SignupResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.user.dto.UserResponses.MyProductResponseDto;
import org.mwt.market.domain.user.dto.UserResponses.UserInfoResponseDto;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = SignupResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody signup(
        @RequestBody SignupRequestDto signupRequestDto) {
        User newUser = userService.registerUser(signupRequestDto);
        return DataResponseBody.success(
            SignupResponseDto.builder()
                .id(newUser.getUserId())
                .email(newUser.getEmail())
                .phone(newUser.getTel())
                .nickname(newUser.getNickname())
                .build()
        );
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = LoginResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody login(
        @RequestBody LoginRequestDto loginRequestDto) {
        throw new RuntimeException("로그인 기능은 필터에서 처리되어야 합니다.");
    }

    @GetMapping("/myInfo")
    @Operation(summary = "사용자 정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = UserInfoResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody info(
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User findUser = userService.readUser(userPrincipal);
        return DataResponseBody.success(
            UserInfoResponseDto.builder()
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
        @ApiResponse(responseCode = "200",
            content = {
                @Content(schema = @Schema(implementation = ProfileUpdateResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody updateProfile(
        @ModelAttribute ProfileUpdateRequestDto profileUpdateRequestDto,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User updatedUser = userService.updateUser(userPrincipal, profileUpdateRequestDto);
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
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = MyProductResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody getMyProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return DataResponseBody.success(
            MyProductResponseDto.builder()
                .build()
        );
    }

    @GetMapping("/myPage/interests")
    @Operation(summary = "내 관심목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = MyInterestResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody getMyInterest(
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return DataResponseBody.success(
            MyInterestResponseDto.builder()
                .build()
        );
    }

    @GetMapping("/myPage/chat")
    @Operation(summary = "나의 채팅방 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = MyChatRoomResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public BaseResponseBody getMyChatRoom(
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return DataResponseBody.success(
            MyChatRoomResponseDto.builder()
                .build()
        );
    }
}
