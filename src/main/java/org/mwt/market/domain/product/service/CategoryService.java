package org.mwt.market.domain.product.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mwt.market.domain.product.dto.CategoryResponseDto;
import org.mwt.market.domain.product.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final ProductCategoryRepository categoryRepo;

    public List<CategoryResponseDto> getCategories() {
        return categoryRepo.findAll().stream()
            .map(CategoryResponseDto::fromEntity)
            .toList();
    }
}
