package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class InvalidNumberOfImagesException extends BaseException {

    public InvalidNumberOfImagesException() {
        super(ErrorCode.InvalidNumberOfImages.getMsg(), ErrorCode.InvalidNumberOfImages);
    }

    public InvalidNumberOfImagesException(String message) {
        super(message, ErrorCode.InvalidNumberOfImages);
    }

    public InvalidNumberOfImagesException(String message, Throwable cause) {
        super(message, cause, ErrorCode.InvalidNumberOfImages);
    }

    public InvalidNumberOfImagesException(Throwable cause) {
        super(cause, ErrorCode.InvalidNumberOfImages);
    }

    public InvalidNumberOfImagesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.InvalidNumberOfImages);
    }
}