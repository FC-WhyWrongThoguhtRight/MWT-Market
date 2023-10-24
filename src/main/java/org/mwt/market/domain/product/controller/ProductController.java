package org.mwt.market.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.dto.ChatRoomDto;
import org.mwt.market.domain.product.dto.ProductChatRoomDto;
import org.mwt.market.domain.product.dto.ProductInfoDto;
import org.mwt.market.domain.product.dto.ProductRequestDto;
import org.mwt.market.domain.product.dto.ProductResponseDto;
import org.mwt.market.domain.product.dto.ProductStatusUpdateRequestDto;
import org.mwt.market.domain.product.dto.ProductUpdateRequestDto;
import org.mwt.market.domain.product.exception.ImageTypeException;
import org.mwt.market.domain.product.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "전체 상품 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public DataResponseBody<List<ProductInfoDto>> showAllProducts(
        @RequestParam(required = false) List<String> categoryNames,
        @RequestParam(required = false, defaultValue = "") String searchWord,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (categoryNames == null) {
            categoryNames = Collections.emptyList();
        }
        List<ProductInfoDto> ProductInfos = productService.findAllProducts(categoryNames, searchWord,
            page - 1, pageSize, userPrincipal);
        return DataResponseBody.success(ProductInfos);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public DataResponseBody<ProductResponseDto> registerProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @Valid @ModelAttribute ProductRequestDto request
    ) throws ImageTypeException {
        ProductResponseDto data = productService.addProduct(userPrincipal, request);

        return DataResponseBody.success(data);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "단건 상품 상세 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public DataResponseBody<ProductResponseDto> showProductDetails(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId
    ) {
        ProductResponseDto data = productService.findProduct(userPrincipal, productId);

        return DataResponseBody.success(data);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400")})
    public BaseResponseBody deleteProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId
    ) {
        productService.deleteProduct(userPrincipal, productId);

        return BaseResponseBody.success("상품 삭제완료");
    }

    @PutMapping("/{productId}/status")
    @Operation(summary = "상품 상태 변경", description = "상품상태 코드 1: 판매중, 2:예약중, 3:거래완료")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")})
    public DataResponseBody<ProductResponseDto> updateProductStatus(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId,
        @RequestBody ProductStatusUpdateRequestDto request
    ) {
        ProductResponseDto data = productService.changeStatus(userPrincipal, productId,
            request.getStatus());
        return DataResponseBody.success(data);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400")})
    public BaseResponseBody updateProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId,
        @Valid @ModelAttribute ProductUpdateRequestDto request
    ) {

        productService.updateProduct(userPrincipal, productId, request);

        return BaseResponseBody.success("상품 수정완료");
    }

    @GetMapping("/{productId}/chats")
    @Operation(summary = "상품 관련 채팅방 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema(implementation = BaseResponseBody.class))})})
    public ResponseEntity<? extends DataResponseBody<List<ProductChatRoomDto>>> productChatList(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId
    ) {
        List<ProductChatRoomDto> data = productService.findChats(userPrincipal, productId);

        return ResponseEntity
            .status(200)
            .body(DataResponseBody.success(data));
    }

    @GetMapping("/{productId}/list")
    @Operation(summary = "판매자의 판매상품목록 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")}
    )
    public DataResponseBody<List<ProductInfoDto>> showSellerProducts(
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long productId
    ) {
        List<ProductInfoDto> ProductInfos = productService.findProductsBySellerId(page, pageSize, userPrincipal, productId);

        return DataResponseBody.success(ProductInfos);
    }


    @PostMapping("/{productId}/chats")
    @Operation(summary = "상품의 채팅창에 접속",
            description = "상품채팅창의 정보를 리턴합니다. 기존채팅방이 없으면 새로 생성된 채팅 방을 리턴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")})
    public DataResponseBody<ChatRoomDto> joinChat(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("productId") Long productId) {
        ChatRoomDto chatRoomDto = productService.joinChatRoom(userPrincipal, productId);
        return DataResponseBody.success(chatRoomDto);
    }
}
