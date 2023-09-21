package org.mwt.market.common.response;

import lombok.Getter;

@Getter
public abstract class BaseResponseBody {
    private final Integer statusCode;

    public BaseResponseBody(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
