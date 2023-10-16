package org.mwt.market.domain.user.exception;

public class DuplicateNicknameException extends DuplicateValueException {

    public DuplicateNicknameException() {
    }

    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException(String message, Throwable cause) {
        super(message, cause);
    }
}
