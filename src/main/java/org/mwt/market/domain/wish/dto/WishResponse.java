package org.mwt.market.domain.wish.dto;

import lombok.Builder;
import lombok.Getter;
import org.mwt.market.common.response.BaseResponseBody;

@Getter
public class WishResponse extends BaseResponseBody {
    private Long id;
    private String title;
    private Integer price;
    private String thumbnailImage;
    private String status;
    private Integer likes;

    @Builder
    public WishResponse(Integer statusCode, String message, Long id, String title, Integer price,
        String thumbnailImage, String status, Integer likes) {
        super(statusCode, message);
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnailImage = thumbnailImage;
        this.status = status;
        this.likes = likes;
    }
}
