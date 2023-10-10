package org.mwt.market.domain.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private Integer price;
    private Long categoryId;
    private List<String> images;
}
