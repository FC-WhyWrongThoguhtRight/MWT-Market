package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.exception.ErrorCode;

public class NoPermissionException extends BaseException {

    public NoPermissionException() {
        super(ErrorCode.NoPermission.getMsg(), ErrorCode.NoPermission);
    }

    public NoPermissionException(String message) {
        super(message, ErrorCode.NoPermission);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause, ErrorCode.NoPermission);
    }

    public NoPermissionException(Throwable cause) {
        super(cause, ErrorCode.NoPermission);
    }

    public NoPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, ErrorCode.NoPermission);
    }
}
