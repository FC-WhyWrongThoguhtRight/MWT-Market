package org.mwt.market.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mwt.market.common.response.BaseResponseBody;
import org.mwt.market.domain.chat.dto.ProductChatRequest.ProductChatRequestDto;
import org.mwt.market.domain.chat.dto.ProductChatResponse;
import org.mwt.market.domain.chat.dto.ProductChatResponse.ProductChatResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {


    @GetMapping("/products/{productId}/chat")
    @Operation(summary = "상품 관련 채팅방 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ProductChatRequestDto.class))}),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<ProductChatResponseDto> productChat(
        @RequestBody ProductChatRequestDto productChatRequestDto) {
        //do something
        return ResponseEntity
            .status(200)
            .body(ProductChatResponseDto.builder()
                .statusCode(200)
                .build()
            );
    }

}
