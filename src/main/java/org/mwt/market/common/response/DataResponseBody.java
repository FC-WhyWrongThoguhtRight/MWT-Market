package org.mwt.market.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponseBody<T> extends BaseResponseBody {
    private final T data;

    public DataResponseBody(Integer statusCode, String message, T data) {
        super(statusCode, message);
        this.data = data;
    }

    public static <T> DataResponseBody<T> success(T data) {
        return new DataResponseBody<>(HttpStatus.OK.value(), SUCCESS_MSG, data);
    }

    public static <T> DataResponseBody<T> success(T data, String msg) {
        return new DataResponseBody<>(HttpStatus.OK.value(), msg, data);
    }

    public static <T> DataResponseBody<T> fail(T data) {
        return new DataResponseBody<>(HttpStatus.BAD_REQUEST.value(), FAIL_MSG, data);
    }

    public static <T> DataResponseBody<T> fail(T data, String msg) {
        return new DataResponseBody<>(HttpStatus.BAD_REQUEST.value(), msg, data);
    }
}
