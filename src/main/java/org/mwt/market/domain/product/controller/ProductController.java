package org.mwt.market.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.product.dto.*;
import org.mwt.market.domain.product.service.ProductService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Products", description = "상품 관련 API")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    @Operation(summary = "전체 상품 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public ResponseEntity<? extends DataResponseBody<List<ProductInfoDto>>> showAllProducts(
            @RequestBody ProductSearchRequestDto request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ) {
        List<ProductInfoDto> ProductInfos = productService.findAllProducts(request, userPrincipal);

        return ResponseEntity
                .status(200)
                .body(DataResponseBody.success(ProductInfos));
    }

    @PostMapping
    @Operation(summary = "상품 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public ResponseEntity<? extends DataResponseBody<ProductResponseDto>> registerProduct(
            @AuthenticationPrincipal Principal principal,
            @Valid @ModelAttribute ProductRequestDto request
    ) {
        ProductResponseDto data = ProductResponseDto.builder()
            .title(request.getTitle())
            .price(request.getPrice())
            .categoryId(request.getCategoryId())
            .content(request.getContent())
            .build();
        DataResponseBody<ProductResponseDto> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "단건 상품 상세 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public ResponseEntity<? extends DataResponseBody<ProductResponseDto>> showProductDetails(
            @PathVariable Long productId
    ) {
        ProductResponseDto data = ProductResponseDto.builder()
            .id(productId)
            .build();
        DataResponseBody<ProductResponseDto> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<? extends DataResponseBody<ProductResponseDto>> updateProductStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId,
            @RequestBody ProductStatusUpdateRequestDto request
    ) {
        ProductResponseDto data = productService.changeStatus(userPrincipal, productId, request.getStatus());
        DataResponseBody<ProductResponseDto> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400")})
    public ResponseEntity<? extends DataResponseBody<ProductResponseDto>> updateProduct(
            @AuthenticationPrincipal Principal principal,
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequestDto request
    ) {
        ProductResponseDto data = ProductResponseDto.builder()
            .id(productId)
            .title(request.getTitle())
            .content(request.getContent())
            .price(request.getPrice())
            .categoryId(request.getCategoryId())
            .images(request.getImages())
            .build();
        DataResponseBody<ProductResponseDto> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
    }

    @GetMapping("/categories")
    @Operation(summary = "상품 카테고리 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    private ResponseEntity<? extends DataResponseBody<List<CategoryResponseDto>>> showCategories() {
        List<CategoryResponseDto> data = List.of(new CategoryResponseDto(1L, "카테고리"));
        DataResponseBody<List<CategoryResponseDto>> body = DataResponseBody.success(data);

        return ResponseEntity
                .status(200)
                .body(body);
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
