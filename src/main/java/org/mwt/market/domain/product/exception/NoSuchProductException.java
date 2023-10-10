package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class NoSuchProductException extends BaseException {

    public NoSuchProductException() {
        super(ErrorCode.NoSuchProduct.getMsg(), ErrorCode.NoSuchProduct);
    }

    public NoSuchProductException(String message) {
        super(message, ErrorCode.NoSuchProduct);
    }

    public NoSuchProductException(String message, Throwable cause) {
        super(message, cause, ErrorCode.NoSuchProduct);
    }

    public NoSuchProductException(Throwable cause) {
        super(cause, ErrorCode.NoSuchProduct);
    }

    public NoSuchProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.NoSuchProduct);
    }
}
