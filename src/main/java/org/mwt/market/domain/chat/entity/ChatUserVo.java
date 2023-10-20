package org.mwt.market.domain.chat.entity;

public interface ChatUserVo {

    Long getChatRoomId();

    Long getUserId1();

    String getNickName1();

    Long getUserId2();

    String getNickName2();

    default String getNickName(Long userId) {
        if (userId.equals(getUserId1())) {
            return getNickName1();
        }
        return getNickName2();
    }
}
