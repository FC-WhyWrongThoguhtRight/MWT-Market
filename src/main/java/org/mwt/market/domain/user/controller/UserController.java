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
import org.mwt.market.common.response.BaseResponseBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = SignupResponseDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<? extends BaseResponseBody> signup(
        @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity
            .status(200)
            .body(SignupResponseDto.builder()
                .statusCode(200)
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
    public ResponseEntity<? extends BaseResponseBody> login(
        @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity
            .status(200)
            .body(LoginResponseDto.builder()
                .statusCode(200)
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
    public ResponseEntity<? extends BaseResponseBody> updateProfile(
        @ModelAttribute ProfileUpdateRequestDto profileUpdateRequestDto) {
        return ResponseEntity
            .status(200)
            .body(ProfileUpdateResponseDto.builder()
                .statusCode(200)
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
    public ResponseEntity<? extends BaseResponseBody> getMyInterest() {
        return ResponseEntity
            .status(200)
            .body(MyInterestResponseDto.builder()
                .statusCode(200)
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
    public ResponseEntity<? extends BaseResponseBody> getMyChatRoom() {
        return ResponseEntity
            .status(200)
            .body(MyChatRoomResponseDto.builder()
                .statusCode(200)
                .build()
            );
    }
}
