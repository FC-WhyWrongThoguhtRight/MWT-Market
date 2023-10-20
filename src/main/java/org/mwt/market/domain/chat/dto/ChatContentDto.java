package org.mwt.market.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatContentDto {

    private Long roomId;
    private Long userId;
    private String nickName;
    private String content;
    private LocalDateTime createAt;

}
