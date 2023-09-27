package org.mwt.market.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mwt.market.domain.chat.dto.ChatRoomRequestDto;
import org.mwt.market.domain.chat.dto.ChatRoomResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {

    @PostMapping("/sellers/{productId}/createChat")
    @Operation(summary = "상품에 대한 채팅방 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ChatRoomResponseDto.class))}),
        @ApiResponse(responseCode = "400")})
    public ResponseEntity<ChatRoomResponseDto> createChat(
        @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return ResponseEntity
            .status(200)
            .body(ChatRoomResponseDto.builder()
                .statusCode(200)
                .build());
    }


    @PostMapping("/seller/{productId}/joinChat")
    @Operation(summary = "상품의 채팅창에 접속")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ChatRoomResponseDto.class))}),
        @ApiResponse(responseCode = "400")})
    public ResponseEntity<ChatRoomResponseDto> joinChat(
        @RequestBody ChatRoomRequestDto chatRoomRequestDto){
        return ResponseEntity
            .status(200)
            .body(ChatRoomResponseDto.builder()
                .statusCode(200)
                .build());
    }


}
