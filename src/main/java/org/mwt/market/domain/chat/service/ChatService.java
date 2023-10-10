package org.mwt.market.domain.chat.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.domain.chat.dto.ChatRoomRequestDto;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository,
        ProductRepository productRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ChatRoom joinChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        Optional<ChatRoom> optChatRoom = chatRoomRepository
            .findByBuyer_UserIdAndChatRoomId(chatRoomRequestDto.getUserId(),
                chatRoomRequestDto.getProductId());

        ChatRoom chatRoom = optChatRoom.orElseGet(() -> {
            User buyer = userRepository.findById(chatRoomRequestDto.getUserId())
                .orElseThrow(() -> new NoSuchUserException());
            Product product = productRepository.findById(chatRoomRequestDto.getProductId())
                .orElseThrow(() -> new NoSuchProductException());
            return chatRoomRepository.save(ChatRoom.createChatRoom(buyer, product));
        });

        return chatRoom;
    }
}
