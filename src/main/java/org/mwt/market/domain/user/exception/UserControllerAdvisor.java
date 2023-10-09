package org.mwt.market.domain.user.exception;

import java.io.IOException;
import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.domain.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(UserControllerAdvisor.class);

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<ErrorResponseBody> handleUserRegisterException(UserRegisterException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof NonTransientDataAccessException) {
            if (cause instanceof DataIntegrityViolationException) {
                logger.warn(ex.getMessage(), ex);
            } else {
                logger.error(ex.getMessage(), ex);
            }
            return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
        } else if (cause instanceof TransientDataAccessException) {
            logger.warn(ex.getMessage(), ex);
            return ResponseEntity
                .internalServerError()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
        }
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(NoSuchUserException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<ErrorResponseBody> handleUserUpdateException(UserUpdateException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof IOException) {
            logger.warn(ex.getMessage(), ex);
            return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
        }
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }
}
