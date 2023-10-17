package org.mwt.market.domain.chat.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.dto.ChatRoomDto;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageRequest;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageResponse;
import org.mwt.market.domain.chat.entity.ChatContent;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatContentRepository;
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

    private final ChatContentRepository chatContentRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository,
        ProductRepository productRepository, ChatContentRepository chatContentRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.chatContentRepository = chatContentRepository;
    }

    @Transactional
    public ChatRoomDto joinChatRoom(UserPrincipal userPrincipal, Long productId) {
        Long userId = userPrincipal.getId();

        Optional<ChatRoom> optChatRoom = chatRoomRepository
            .findByBuyer_UserIdAndProduct_ProductId(userId,
                productId);

        ChatRoom chatRoom = optChatRoom.orElseGet(() -> {
            User buyer = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException());
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchProductException());
            return chatRoomRepository.save(ChatRoom.createChatRoom(buyer, product));
        });

        return ChatRoomDto.builder()
            .chatRoomId(chatRoom.getChatRoomId())
            .buyerId(chatRoom.getBuyer().getUserId())
            .nickName(chatRoom.getBuyer().getNickname())
            .buyerProfileImg(chatRoom.getBuyer().getProfileImageUrl())
            //TODO: MongoDB로부터 마지막 메시지 받아오기
            .lastMessage(null)
            .lasteCreatedAt(null)
            .build();
    }

    public MessageResponse saveMessage(String roomId, MessageRequest messageRequest) {

        ChatContent chatContent = ChatContent.builder()
            .chatRoomId(Long.parseLong(roomId))
            .userId(1L)
            .content(messageRequest.getContent())
            .createAt(LocalDateTime.now())
            .build();

        ChatContent result = chatContentRepository.save(chatContent);


        return MessageResponse.builder()
            .name(String.valueOf(result.getUserId()))
            .content(result.getContent())
            .dateTime(result.getCreateAt())
            .build();
    }


}
