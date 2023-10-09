package org.mwt.market.common.response;

import lombok.Getter;

@Getter
public class ErrorResponseBody {

    private final boolean isSuccessful;
    private final Integer statusCode;
    private final String message;
    private Throwable ex;

    public ErrorResponseBody(boolean isSuccessful, Integer statusCode, String message,
        Throwable ex) {
        this.isSuccessful = isSuccessful;
        this.statusCode = statusCode;
        this.message = message;
        this.ex = ex;
    }

    public static ErrorResponseBody unsuccessful(String message) {
        return new ErrorResponseBody(false, 400, message, null);
    }

    public static ErrorResponseBody unsuccessful(String message, Throwable ex) {
        return new ErrorResponseBody(false, 400, message, ex);
    }
}
