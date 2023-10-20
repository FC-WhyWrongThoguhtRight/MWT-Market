package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class ImageTypeException extends BaseException  {

    public ImageTypeException() {
        super(ErrorCode.InvalidImageType.getMsg(), ErrorCode.InvalidImageType);
    }

    public ImageTypeException(String message) {
        super(message, ErrorCode.InvalidImageType);
    }

    public ImageTypeException(String message, Throwable cause) {
        super(message, cause, ErrorCode.InvalidImageType);
    }

    public ImageTypeException(Throwable cause) {
        super(cause, ErrorCode.InvalidImageType);
    }

    public ImageTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.InvalidImageType);
    }
}
