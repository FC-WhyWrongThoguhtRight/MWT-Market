package org.mwt.market.domain.product.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private Integer price;
    private Integer category;
    private List<String> images;
}
