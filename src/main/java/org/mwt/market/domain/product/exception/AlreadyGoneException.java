package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class AlreadyGoneException extends BaseException {

    public AlreadyGoneException() {
        super(ErrorCode.AlreadyGone.getMsg(), ErrorCode.AlreadyGone);
    }

    public AlreadyGoneException(String message) {
        super(message, ErrorCode.AlreadyGone);
    }

    public AlreadyGoneException(String message, Throwable cause) {
        super(message, cause, ErrorCode.AlreadyGone);
    }

    public AlreadyGoneException(Throwable cause) {
        super(cause, ErrorCode.AlreadyGone);
    }

    public AlreadyGoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.AlreadyGone);
    }
}
