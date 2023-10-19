package org.mwt.market.domain.product.exception;

import org.mwt.market.common.response.ErrorResponseBody;
import org.mwt.market.domain.product.controller.ProductController;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.wish.controller.WishController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = { ProductController.class, WishController.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(ProductControllerAdvisor.class);

    @ExceptionHandler(value = {
        NoSuchProductException.class, NoSuchStatusException.class, InvalidNumberOfImagesException.class,
        ImageUploadErrorException.class, NoSuchUserException.class, ImageDeleteErrorException.class,
        InvalidNumberOfImagesException.class, NoSuchCategoryException.class, ImageTypeExcpetion.class
    })
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(NoSuchProductException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
            .badRequest()
            .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }

    @ExceptionHandler(NoSuchCategoryException.class)
    public ResponseEntity<ErrorResponseBody> handleUserInfoException(NoSuchCategoryException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponseBody.unsuccessful(ex.getMessage(), ex));
    }
}
