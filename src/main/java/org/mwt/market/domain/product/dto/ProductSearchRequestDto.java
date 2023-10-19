package org.mwt.market.domain.product.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDto {

    private List<String> categoryNames = Collections.emptyList();
    private String searchWord = "";
    private Integer page;
    private Integer pageSize;
}
