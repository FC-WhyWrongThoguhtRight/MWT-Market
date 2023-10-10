package org.mwt.market.domain.wish.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.domain.wish.dto.WishReqeuestDto;
import org.mwt.market.domain.wish.dto.WishResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wish")
@Tag(name = "Wish", description = "관심목록 관련 API")
public class WishController {

    @GetMapping
    @Operation(summary = "내 관심목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = BaseResponseBody.class))})
    })
    public ResponseEntity<? extends DataResponseBody<List<WishResponseDto>>> getMyInterest() {
        List<WishResponseDto> data = List.of(
            new WishResponseDto(0L, "title", 0, "thumbnail", "status", 0)
        );

        return ResponseEntity
            .status(200)
            .body(DataResponseBody.success(data));
    }

    @PostMapping
    @Operation(summary = "내 관심목록 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = BaseResponseBody.class))})
    })
    public ResponseEntity<? extends BaseResponseBody> addMyInterest(
        @RequestBody WishReqeuestDto wishReqeuestDto
    ) {
        return ResponseEntity
            .status(200)
            .body(BaseResponseBody.success());
    }

    @DeleteMapping
    @Operation(summary = "내 관심목록 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
            content = {@Content(schema = @Schema(implementation = BaseResponseBody.class))})
    })
    public ResponseEntity<? extends BaseResponseBody> removeMyInterest(
        @RequestBody WishReqeuestDto wishReqeuestDto
    ) {
        return ResponseEntity
            .status(200)
            .body(BaseResponseBody.success());
    }
}