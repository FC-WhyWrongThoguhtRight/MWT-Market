package org.mwt.market.domain.product.exception;

public class ProductUpdateException extends RuntimeException{

    public ProductUpdateException() {
    }

    public ProductUpdateException(String message) {
        super(message);
    }

    public ProductUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
