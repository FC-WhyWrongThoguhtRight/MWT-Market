package org.mwt.market.domain.wish.exception;

import org.mwt.market.common.exception.BaseException;
import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.domain.wish.controller.WishController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = { WishController.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WishControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(WishControllerAdvisor.class);

    @ExceptionHandler(value = { AlreadyExistWishException.class, NoSuchWishException.class } )
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(BaseException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }
}
