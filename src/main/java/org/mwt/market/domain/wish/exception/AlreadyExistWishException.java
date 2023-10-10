package org.mwt.market.domain.wish.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class AlreadyExistWishException extends BaseException {

    public AlreadyExistWishException() {
        super(ErrorCode.AlreadyExistWish.getMsg(), ErrorCode.AlreadyExistWish);
    }

    public AlreadyExistWishException(String message) {
        super(message, ErrorCode.AlreadyExistWish);
    }

    public AlreadyExistWishException(String message, Throwable cause) {
        super(message, cause, ErrorCode.AlreadyExistWish);
    }

    public AlreadyExistWishException(Throwable cause) {
        super(cause, ErrorCode.AlreadyExistWish);
    }

    public AlreadyExistWishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.AlreadyExistWish);
    }
}
