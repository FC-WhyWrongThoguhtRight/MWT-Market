package org.mwt.market.domain.chat.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.domain.chat.dto.ChatRoomRequestDto;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }


    public ChatRoom joinChatRoom(ChatRoomRequestDto chatRoomRequestDto) {

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository
            .findByBuyerIdAndProductId(chatRoomRequestDto.getUserId(),
                chatRoomRequestDto.getProductId());

        if (chatRoomOptional.isEmpty()) {
            chatRoomRepository.save(
                ChatRoom.createChatRoom(chatRoomRequestDto)
            );
            chatRoomOptional = chatRoomRepository.findByBuyerIdAndProductId(
                chatRoomRequestDto.getUserId(), chatRoomRequestDto.getProductId());
        }

        return chatRoomOptional.orElseThrow();


    }
}
