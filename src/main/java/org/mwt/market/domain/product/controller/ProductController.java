package org.mwt.market.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.product.dto.*;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.vo.ProductCategoryType;
import org.mwt.market.domain.user.dto.UserResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "Products", description = "상품 관련 API")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/list")
    @Operation(summary = "전체 상품 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductListResponseDto.class))})})
    public ResponseEntity<? extends BaseResponseBody> showAllProducts(String searchWord){
        return ResponseEntity
                .status(200)
                .body(ProductListResponseDto.builder()
                        .statusCode(200)
                        .build());
    }

    @PostMapping
    @Operation(summary = "상품 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})})
    public ResponseEntity<? extends BaseResponseBody> registerProduct(
            @RequestHeader("Authorization") String authorization,
            @Valid @ModelAttribute ProductRequestDto request
            ) {
        return ResponseEntity
                .status(200)
                .body(ProductResponseDto.builder()
                        .statusCode(200)
                        .title(request.getTitle())
                        .price(request.getPrice())
                        .category(request.getCategory())
                        .content(request.getContent())
                        .build());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "단건 상품 상세 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})})
    public ResponseEntity<? extends BaseResponseBody> showProductDetails(
            @PathVariable Long productId
    ) {
        return ResponseEntity
                .status(200)
                .body(ProductResponseDto.builder()
                        .statusCode(200)
                        .id(productId)
                        .build());
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
    public void deleteProduct(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long productId
    ) {
    }

    @PutMapping("/{productId}/status")
    @Operation(summary = "상품 상태 변경")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})})
    public ResponseEntity<? extends BaseResponseBody> updateProductStatus(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long productId,
            @RequestBody ProductStatusUpdateRequestDto request
    ) {
        return ResponseEntity
                .status(200)
                .body(ProductResponseDto.builder()
                        .statusCode(200)
                        .id(productId)
                        .status(request.getStatus())
                        .build());
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400")})
    public ResponseEntity<? extends BaseResponseBody> updateProduct(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequestDto request
    ) {
        return ResponseEntity
                .status(200)
                .body(ProductResponseDto.builder()
                        .statusCode(200)
                        .id(productId)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .price(request.getPrice())
                        .category(request.getCategory())
                        .images(request.getImages())
                        .build());
    }

    @GetMapping("/categories")
    @Operation(summary = "상품 카테고리 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProductCategoryResponseDto.class))})})
    private ResponseEntity<? extends BaseResponseBody> showCategories() {
        return ResponseEntity
                .status(200)
                .body(ProductCategoryResponseDto.builder()
                        .statusCode(200)
                        .build());
    }

    @GetMapping("/{productId}/chats")
    @Operation(summary = "상품 관련 채팅방 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ProductChatsResponseDto.class))}),
            @ApiResponse(responseCode = "400")})
    public ResponseEntity<? extends BaseResponseBody> productChatList(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String productId
    ) {
        return ResponseEntity
                .status(200)
                .body(ProductChatsResponseDto.builder()
                        .statusCode(200)
                        .build());
    }
}
