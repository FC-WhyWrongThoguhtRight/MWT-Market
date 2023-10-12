package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class ImageDeleteErrorException extends BaseException {

    public ImageDeleteErrorException() {
        super(ErrorCode.ImageDeleteError.getMsg(), ErrorCode.ImageDeleteError);
    }

    public ImageDeleteErrorException(String message) {
        super(message, ErrorCode.ImageDeleteError);
    }

    public ImageDeleteErrorException(String message, Throwable cause) {
        super(message, cause, ErrorCode.ImageDeleteError);
    }

    public ImageDeleteErrorException(Throwable cause) {
        super(cause, ErrorCode.ImageDeleteError);
    }

    public ImageDeleteErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.ImageDeleteError);
    }
}
