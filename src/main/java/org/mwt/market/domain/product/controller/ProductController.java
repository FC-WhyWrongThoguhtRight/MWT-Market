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
    public DataResponseBody<List<ProductInfoDto>> showAllProducts(
            @RequestBody ProductSearchRequestDto request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ) {
        List<ProductInfoDto> ProductInfos = productService.findAllProducts(request, userPrincipal);

        return DataResponseBody.success(ProductInfos);
    }

    @PostMapping
    @Operation(summary = "상품 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public BaseResponseBody registerProduct(
            @AuthenticationPrincipal Principal principal,
            @Valid @ModelAttribute ProductRequestDto request
    ) {
        return BaseResponseBody.success("상품 등록완료");
    }

    @GetMapping("/{productId}")
    @Operation(summary = "단건 상품 상세 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public DataResponseBody<ProductResponseDto> showProductDetails(
            @PathVariable Long productId
    ) {
        ProductResponseDto data = ProductResponseDto.builder()
            .id(productId)
            .build();

        return DataResponseBody.success(data);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
    public BaseResponseBody deleteProduct(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId, userPrincipal);
        
        return BaseResponseBody.success("상품 삭제완료");
    }

    @PutMapping("/{productId}/status")
    @Operation(summary = "상품 상태 변경")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")})
    public DataResponseBody<ProductResponseDto> updateProductStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId,
            @RequestBody ProductStatusUpdateRequestDto request
    ) {
        ProductResponseDto data = productService.changeStatus(userPrincipal, productId, request.getStatus());
        DataResponseBody<ProductResponseDto> body = DataResponseBody.success(data);

        return DataResponseBody.success(data);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400")})
    public BaseResponseBody updateProduct(
            @AuthenticationPrincipal Principal principal,
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequestDto request
    ) {

        productService.updateProduct(productId, request);

        return BaseResponseBody.success("상품 수정완료");
    }

    @GetMapping("/{productId}/chats")
    @Operation(summary = "상품 관련 채팅방 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema(implementation = BaseResponseBody.class))})})
    public ResponseEntity<? extends DataResponseBody<List<ProductChatResponseDto>>> productChatList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId
    ) {
        List<ProductChatResponseDto> data = productService.findChats(userPrincipal, productId);

        return ResponseEntity
                .status(200)
                .body(DataResponseBody.success(data));
    }
}
