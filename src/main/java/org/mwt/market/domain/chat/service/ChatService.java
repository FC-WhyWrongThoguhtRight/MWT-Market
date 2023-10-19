package org.mwt.market.domain.chat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.dto.ChatContentDto;
import org.mwt.market.domain.chat.dto.ChatRoomDto;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageRequest;
import org.mwt.market.domain.chat.dto.ChatWsDto.MessageResponse;
import org.mwt.market.domain.chat.entity.ChatContent;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.entity.ChatUserVo;
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
                .orElseThrow(NoSuchUserException::new);
            Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchProductException::new);
            return chatRoomRepository.save(ChatRoom.createChatRoom(buyer, product));
        });

        ChatContent lastMessage = chatContentRepository.findFirstByChatRoomIdOrderByCreateAtDesc(
            optChatRoom
                .get()
                .getChatRoomId());

        return ChatRoomDto.builder()
            .chatRoomId(chatRoom.getChatRoomId())
            .buyerId(chatRoom.getBuyer().getUserId())
            .nickName(chatRoom.getBuyer().getNickname())
            .buyerProfileImg(chatRoom.getBuyer().getProfileImageUrl())
            .lastMessage(lastMessage == null ? null : lastMessage.getContent())
            .lastCreatedAt(lastMessage == null ? null : lastMessage.getCreateAt())
            .build();
    }

    public MessageResponse saveMessage(String roomId, MessageRequest messageRequest) {

        ChatContent chatContent = ChatContent.builder()
            .chatRoomId(Long.parseLong(roomId))
            .userId(messageRequest.getUserId())
            .content(messageRequest.getContent())
            .createAt(LocalDateTime.now())
            .build();

        ChatContent result = chatContentRepository.save(chatContent);

        return MessageResponse.builder()
            .userId(result.getUserId())
            .content(result.getContent())
            .dateTime(result.getCreateAt())
            .build();
    }

    public List<ChatContentDto> getChatContents(Long chatRoomId) {

        /*
         * 채팅의 내역을 조회할때 User의 정보도 필요하다.
         * 그런데 두정보는 서로다른 Datasource에 저장되어 있어 따로 조회를 하고
         * 합쳐야 함.
         * 또한 UserId는 가지고 있지만 해당 정보는 chatroom의 buyer_userId와 productId의 seller가 가지고 있다.
         * 두 정보를 가지고 조회하여 구매자와 판매자의 사용자 정보를 조회하고, chatContentDtoList를 작성한다.
         */

        List<ChatContent> chatContents = chatContentRepository
            .findAllByChatRoomIdOrderByCreateAtAsc(chatRoomId);
        ChatUserVo chatUserVo = chatRoomRepository.findChatroomUsers(chatRoomId);

        List<ChatContentDto> chatContentDtos = new ArrayList<>();
        for (ChatContent cc : chatContents) {
            chatContentDtos.add(ChatContentDto.builder()
                .roomId(cc.getChatRoomId())
                .userId(cc.getUserId())
                .nickName(chatUserVo.getNickName(cc.getUserId()))
                .content(cc.getContent())
                .createAt(cc.getCreateAt())
                .build()
            );
        }

        return chatContentDtos;
    }

}
