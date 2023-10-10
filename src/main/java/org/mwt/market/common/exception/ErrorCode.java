package org.mwt.market.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NoSuchElement(400, "noSuchElement"),
    NoSuchProduct(400, "noSuchProduct"),
    AlreadyExistWish(400, "alreadyExistWish")
    ;

    private final Integer code;
    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
