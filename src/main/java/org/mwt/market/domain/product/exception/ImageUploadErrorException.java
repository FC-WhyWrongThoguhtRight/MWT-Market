package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class ImageUploadErrorException extends BaseException {

    public ImageUploadErrorException() {
        super(ErrorCode.ImageUploadError.getMsg(), ErrorCode.ImageUploadError);
    }

    public ImageUploadErrorException(String message) {
        super(message, ErrorCode.ImageUploadError);
    }

    public ImageUploadErrorException(String message, Throwable cause) {
        super(message, cause, ErrorCode.ImageUploadError);
    }

    public ImageUploadErrorException(Throwable cause) {
        super(cause, ErrorCode.ImageUploadError);
    }

    public ImageUploadErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.ImageUploadError);
    }
}
