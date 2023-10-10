package org.mwt.market.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.domain.product.dto.ProductCategoryResponseDto;
import org.mwt.market.domain.product.dto.ProductChatResponseDto;
import org.mwt.market.domain.product.dto.ProductInfoDto;
import org.mwt.market.domain.product.dto.ProductListResponseDto;
import org.mwt.market.domain.product.dto.ProductRequestDto;
import org.mwt.market.domain.product.dto.ProductResponseDto;
import org.mwt.market.domain.product.dto.ProductStatusUpdateRequestDto;
import org.mwt.market.domain.product.dto.ProductUpdateRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Products", description = "상품 관련 API")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/list")
    @Operation(summary = "전체 상품 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductListResponseDto.class))})}
    )
    public ResponseEntity<? extends BaseResponseBody> showAllProducts(
            @RequestParam(required = false) String searchWord
    ) {
        List<ProductInfoDto> productInfos = new ArrayList<>();

        return ResponseEntity
                .status(200)
                .body(DataResponseBody.success(ProductListResponseDto.of(productInfos)));
    }

    @PostMapping
    @Operation(summary = "상품 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})}
    )
    public ResponseEntity<? extends BaseResponseBody> registerProduct(
            @AuthenticationPrincipal Principal principal,
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
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})}
    )
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
            @AuthenticationPrincipal Principal principal,
            @PathVariable Long productId
    ) {
    }

    @PutMapping("/{productId}/status")
    @Operation(summary = "상품 상태 변경")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))})}
    )
    public ResponseEntity<? extends BaseResponseBody> updateProductStatus(
            @AuthenticationPrincipal Principal principal,
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
            @AuthenticationPrincipal Principal principal,
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
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProductCategoryResponseDto.class))})})
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
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema(implementation = BaseResponseBody.class))})})
    public ResponseEntity<? extends DataResponseBody<List<ProductChatResponseDto>>> productChatList(
            @AuthenticationPrincipal Principal principal,
            @PathVariable String productId
    ) {
        List<ProductChatResponseDto> data = List.of(
            ProductChatResponseDto.builder()
                .thumbnail("thumbnail")
                .statusCode(200)
                .build()
        );

        DataResponseBody<List<ProductChatResponseDto>> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
    }
}
