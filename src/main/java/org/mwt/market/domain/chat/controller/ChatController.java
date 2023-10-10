package org.mwt.market.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.domain.chat.dto.ChatRoomRequestDto;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

// 채팅방 접속할때 채팅방이 없으면 신규 채팅방 생성후 리턴 하도록 만들었습니다.

//    @PostMapping("/sellers/{productId}/createChat")
//    @Operation(summary = "상품에 대한 채팅방 생성")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200",
//            content = {@Content(schema = @Schema(implementation = ChatRoomResponseDto.class))}),
//        @ApiResponse(responseCode = "400")})
//    public ResponseEntity<ChatRoomResponseDto> createChat(
//        @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
//        return ResponseEntity
//            .status(200)
//            .body(ChatRoomResponseDto.builder()
//                .statusCode(200)
//                .build());
//    }

    @PostMapping("/seller/{productId}/joinChat")
    @Operation(summary = "상품의 채팅창에 접속")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400")})
    public DataResponseBody<ChatRoom> joinChat(
        @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        ChatRoom chatRoom = chatService.joinChatRoom(chatRoomRequestDto);
        return DataResponseBody.success(chatRoom);
    }
}
