package org.mwt.market.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NoSuchElement(400, "noSuchElement"),
    NoSuchProduct(400, "noSuchProduct"),
    NoSuchCategory(400, "noSuchCategory"),
    AlreadyExistWish(400, "alreadyExistWish"),
    NoSuchWish(400, "noSuchWish"),
    NoPermission(401, "noPermission"),
    ImageUploadError(400, "이미지 업로드 중 오류 발생"),
    ImageDeleteError(400, "이미지 삭제 중 오류 발생"),
    InvalidImageType(400, "유효하지 않은 이미지 형식입니다");

    private final Integer code;
    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
