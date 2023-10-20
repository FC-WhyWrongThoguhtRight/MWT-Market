package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class NoSuchCategoryException extends BaseException {
    public NoSuchCategoryException() {
        super(ErrorCode.NoSuchCategory.getMsg(), ErrorCode.NoSuchCategory);
    }

    public NoSuchCategoryException(String message) {
        super(message, ErrorCode.NoSuchCategory);
    }

    public NoSuchCategoryException(String message, Throwable cause) {
        super(message, cause, ErrorCode.NoSuchCategory);
    }

    public NoSuchCategoryException(Throwable cause) {
        super(cause, ErrorCode.NoSuchCategory);
    }

    public NoSuchCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.NoSuchCategory);
    }
}
