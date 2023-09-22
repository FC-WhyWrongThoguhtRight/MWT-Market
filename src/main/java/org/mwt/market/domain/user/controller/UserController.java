package org.mwt.market.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mwt.market.common.response.BaseResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import static org.mwt.market.domain.user.dto.UserResponses.SignupResponseDto;


@RestController
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = SignupResponseDto.class))}),
            @ApiResponse(responseCode = "400")})
    public ResponseEntity<? extends BaseResponseBody> signup(
            @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity
                .status(200)
                .body(SignupResponseDto.builder()
                        .statusCode(200)
                        .build()
                );
    }
}
