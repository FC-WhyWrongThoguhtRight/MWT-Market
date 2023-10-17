package org.mwt.market.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.dto.ChatRoomDto;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageRequest;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageResponse;
import org.mwt.market.domain.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/seller/{productId}/joinChat")
    @Operation(summary = "상품의 채팅창에 접속")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400")})
    public DataResponseBody<ChatRoomDto> joinChat(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable("productId") Long productId) {
        ChatRoomDto chatRoomDto = chatService.joinChatRoom(userPrincipal, productId);
        return DataResponseBody.success(chatRoomDto);
    }

    @MessageMapping("/room/{roomId}")
    @SendTo("/sub/room/{roomId}")
    @Operation(summary = "상품 판매자와 1:1 채팅 메시지 전송 및 내역 저장")
    @ApiResponses()
    public MessageResponse chat(@DestinationVariable String roomId, MessageRequest message) {
        return chatService.saveMessage(roomId, message);
    }
}
