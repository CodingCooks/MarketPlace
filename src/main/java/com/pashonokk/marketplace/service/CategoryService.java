package com.pashonokk.marketplace.service;

import com.pashonokk.marketplace.dto.CategoryRequestDto;
import com.pashonokk.marketplace.dto.CategorySavingDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.entity.Category;
import com.pashonokk.marketplace.mapper.CategoryRequestMapper;
import com.pashonokk.marketplace.mapper.CategorySavingMapper;
import com.pashonokk.marketplace.mapper.PageMapper;
import com.pashonokk.marketplace.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategorySavingMapper categorySavingMapper;
    private final PageMapper pageMapper;
    private static final String ERROR_MESSAGE = "Category with id %s doesn't exist";

    @Transactional(readOnly = true)
    public CategoryRequestDto getCategory(Long id) {
        return categoryRepository.findById(id).map(categoryRequestMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_MESSAGE, id)));
    }

    @Transactional(readOnly = true)
    public PageResponse<CategoryRequestDto> getAllCategories(Pageable pageable) {
        return pageMapper.toPageResponse(categoryRepository.findAll(pageable).map(categoryRequestMapper::toDto));
    }

    @Transactional
    public CategoryRequestDto addCategory(CategorySavingDto categorySavingDto) {
        Category category = categorySavingMapper.toEntity(categorySavingDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryRequestMapper.toDto(savedCategory);
    }
}
