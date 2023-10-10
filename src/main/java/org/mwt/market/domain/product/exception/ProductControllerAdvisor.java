package org.mwt.market.domain.product.exception;

import java.io.IOException;
import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.domain.product.controller.ProductController;
import org.mwt.market.domain.user.controller.UserController;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.exception.UserRegisterException;
import org.mwt.market.domain.user.exception.UserUpdateException;
import org.mwt.market.domain.wish.controller.WishController;
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

@RestControllerAdvice(basePackageClasses = { ProductController.class, WishController.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(ProductControllerAdvisor.class);

    @ExceptionHandler(value = {
        NoSuchProductException.class, NoSuchStatusException.class, NoPermissionException.class
    })
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(NoSuchProductException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }
}
