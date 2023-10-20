package org.mwt.market.domain.product.exception;

import org.mwt.market.common.exception.BaseException;

public class ImageTypeExcpetion extends BaseException  {

    public ImageTypeExcpetion() {
    }

    public ImageTypeExcpetion(String message) {
        super(message);
    }

    public ImageTypeExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
