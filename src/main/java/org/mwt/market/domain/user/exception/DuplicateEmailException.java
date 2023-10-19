package org.mwt.market.domain.user.exception;

public class DuplicateEmailException extends DuplicateValueException {

    public DuplicateEmailException() {
    }

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
