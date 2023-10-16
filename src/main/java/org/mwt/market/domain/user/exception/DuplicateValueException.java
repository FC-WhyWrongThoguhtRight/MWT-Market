package org.mwt.market.domain.user.exception;

public class DuplicateValueException extends UserRegisterException {

    public DuplicateValueException() {
    }

    public DuplicateValueException(String message) {
        super(message);
    }

    public DuplicateValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
