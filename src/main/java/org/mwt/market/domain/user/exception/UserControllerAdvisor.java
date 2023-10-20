package org.mwt.market.domain.user.exception;

import java.io.IOException;
import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.domain.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(UserControllerAdvisor.class);

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<ErrorResponseBody> handleUserRegisterException(UserRegisterException ex) {
        if (ex instanceof DuplicateValueException) {
            Integer statusCode = 400;
            if (ex instanceof DuplicateEmailException) {
                statusCode = 401;
            } else if (ex instanceof DuplicatePhoneException) {
                statusCode = 402;
            } else if (ex instanceof DuplicateNicknameException) {
                statusCode = 403;
            }
            return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(statusCode, ex.getMessage()));
        }
        Throwable cause = ex.getCause();
        if (cause instanceof NonTransientDataAccessException) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
        } else if (cause instanceof TransientDataAccessException) {
            logger.warn(ex.getMessage(), ex);
            return ResponseEntity
                .internalServerError()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
        }
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<ErrorResponseBody> handleUserUpdateException(UserUpdateException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof IOException || cause instanceof DuplicateNicknameException) {
            logger.warn(ex.getMessage(), ex);
            return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
        }
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(NoSuchUserException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponseBody.unsuccessful(ex.getMessage()));
    }
}
