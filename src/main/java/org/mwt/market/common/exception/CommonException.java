package org.mwt.market.common.exception;

public class CommonException extends BaseException {

    public CommonException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CommonException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CommonException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public CommonException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public CommonException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ErrorCode errorCode
    ) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
