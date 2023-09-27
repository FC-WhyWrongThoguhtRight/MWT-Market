package org.mwt.market.domain.chat.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import lombok.Getter;

/**
 * 채팅 내용의 리스트를 전달하기 위한 일급컬렉션입니다.
 * API Response가 아니라 백엔드 내부에서의 자료 전달을 위해 만들었습니다.
 */
@Getter
public class ChatRoomListDto implements Iterable<ChatRoomDto>{

    private final List<ChatRoomDto> chatRoomDtoList;

    public ChatRoomListDto(List<ChatRoomDto> chatRoomDtoList) {
        this.chatRoomDtoList = chatRoomDtoList;
    }
    @Override
    public Iterator<ChatRoomDto> iterator() {
        return chatRoomDtoList.iterator();
    }

}
