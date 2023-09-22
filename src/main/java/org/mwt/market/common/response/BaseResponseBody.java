package org.mwt.market.common.response;

import lombok.Getter;

@Getter
public abstract class BaseResponseBody {
    private final Integer statusCode;
    private final String message;

    public BaseResponseBody(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
