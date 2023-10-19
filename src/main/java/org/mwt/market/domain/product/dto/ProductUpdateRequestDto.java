package org.mwt.market.domain.product.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    @NotBlank(message = "상품 제목은 필수 항목입니다.")
    @Schema(description = "상품 제목", example = "test_title")
    private String title;

    @NotBlank(message = "상품 내용은 필수 항목입니다.")
    @Schema(description = "상품 설명", example = "test_content")
    private String content;

    @Schema(description = "상품 가격")
    private Integer price;

    @NotNull(message = "상품 카테고리는 필수 항목입니다.")
    @Schema(description = "상품 카테고리", example = "test_category")
    private String categoryName;

    @Schema(description = "상품 관련 이미지")
    private List<MultipartFile> images;

}
