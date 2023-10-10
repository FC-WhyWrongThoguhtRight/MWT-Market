package org.mwt.market.domain.product.dto;

import java.util.List;
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

    private Long id;
    private String title;
    private String content;
    private Integer price;
    private Long categoryId;
    private List<MultipartFile> images;
}
