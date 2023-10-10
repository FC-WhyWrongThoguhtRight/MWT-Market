package org.mwt.market.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDto {

    private List<Integer> categoryIds;
    private String searchWord;
    private Integer page;
    private Integer pageSize;
}
