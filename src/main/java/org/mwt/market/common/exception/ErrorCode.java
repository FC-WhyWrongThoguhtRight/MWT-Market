package org.mwt.market.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NoSuchElement(400, "noSuchElement");

    private final Integer code;
    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
