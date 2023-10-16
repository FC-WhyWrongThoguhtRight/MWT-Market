package org.mwt.market.domain.product.exception;

public class ImageTypeExcpetion extends RuntimeException{

    public ImageTypeExcpetion() {
    }

    public ImageTypeExcpetion(String message) {
        super(message);
    }

    public ImageTypeExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
