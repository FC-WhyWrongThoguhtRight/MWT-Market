package org.mwt.market.domain.wish.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class NoSuchWishException extends BaseException {

    public NoSuchWishException() {
        super(ErrorCode.NoSuchWish.getMsg(), ErrorCode.NoSuchWish);
    }

    public NoSuchWishException(String message) {
        super(message, ErrorCode.NoSuchWish);
    }

    public NoSuchWishException(String message, Throwable cause) {
        super(message, cause, ErrorCode.NoSuchWish);
    }

    public NoSuchWishException(Throwable cause) {
        super(cause, ErrorCode.NoSuchWish);
    }

    public NoSuchWishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.NoSuchWish);
    }
}
