package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class NoSuchStatusException extends BaseException {

    public NoSuchStatusException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchStatusException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NoSuchStatusException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public NoSuchStatusException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public NoSuchStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
