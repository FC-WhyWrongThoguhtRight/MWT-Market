package org.mwt.market.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.common.response.DataResponseBody;
import org.mwt.market.domain.product.dto.CategoryResponseDto;
import org.mwt.market.domain.product.service.CategoryService;
import org.mwt.market.domain.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Products", description = "상품 관련 API")
@RequestMapping("/products")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "상품 카테고리 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    private ResponseEntity<? extends DataResponseBody<List<CategoryResponseDto>>> showCategories() {
        List<CategoryResponseDto> data = categoryService.getCategories();

        return ResponseEntity
            .status(200)
            .body(DataResponseBody.success(data));
    }
}
