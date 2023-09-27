package org.mwt.market.domain.wish.controller;

import static org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import static org.mwt.market.domain.user.dto.UserResponses.SignupResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.wish.dto.WishResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Wish", description = "관심 목록 관련 API")
public class WishController {
    @GetMapping("/myPage/interests")
    @Operation(summary = "관심 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = WishResponse.class))}),
            @ApiResponse(responseCode = "401")})
    public ResponseEntity<? extends BaseResponseBody> signup(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity
                .status(200)
                .body(
                    WishResponse.builder()
                        .id(1L)
                        .price(10000)
                        .likes(0)
                        .title("제목")
                        .thumbnailImage("https://imgur.com/ABCDE")
                        .status("상태")
                        .build()
                );
    }
}
