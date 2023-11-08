package com.pashonokk.marketplace.service;

import com.pashonokk.marketplace.dto.CategoryResponseDto;
import com.pashonokk.marketplace.dto.CategorySavingDto;
import com.pashonokk.marketplace.dto.SubCategoryResponseDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.entity.Category;
import com.pashonokk.marketplace.mapper.CategoryResponseMapper;
import com.pashonokk.marketplace.mapper.CategorySavingMapper;
import com.pashonokk.marketplace.mapper.PageMapper;
import com.pashonokk.marketplace.mapper.SubCategoryResponseMapper;
import com.pashonokk.marketplace.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CategorySavingMapper categorySavingMapper;
    private final SubCategoryResponseMapper subCategoryResponseMapper;
    private final PageMapper pageMapper;
    private static final String ERROR_MESSAGE = "Category with id %s doesn't exist";

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(Long id) {
        return categoryRepository.findById(id).map(categoryResponseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_MESSAGE, id)));
    }

    @Transactional(readOnly = true)
    public List<SubCategoryResponseDto> getCategorySubCategories(Long id) {
        Category category = categoryRepository.findByIdWithSubCategories(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_MESSAGE, id)));
        return category.getSubCategories().stream().map(subCategoryResponseMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return pageMapper.toPageResponse(categoryRepository.findAll(pageable).map(categoryResponseMapper::toDto));
    }

    @Transactional
    public CategoryResponseDto addCategory(CategorySavingDto categorySavingDto) {
        Category category = categorySavingMapper.toEntity(categorySavingDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryResponseMapper.toDto(savedCategory);
    }
}
