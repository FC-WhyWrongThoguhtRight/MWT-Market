package org.mwt.market.domain.user.exception;

public class UserRegisterException extends RuntimeException {

    public UserRegisterException() {
    }

    public UserRegisterException(String message) {
        super(message);
    }

    public UserRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
