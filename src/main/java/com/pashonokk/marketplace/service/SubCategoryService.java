package com.pashonokk.marketplace.service;

import com.pashonokk.marketplace.dto.SubCategoryResponseDto;
import com.pashonokk.marketplace.dto.SubCategorySavingDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.entity.Category;
import com.pashonokk.marketplace.entity.SubCategory;
import com.pashonokk.marketplace.exception.CategoryDoesntExistException;
import com.pashonokk.marketplace.mapper.PageMapper;
import com.pashonokk.marketplace.mapper.SubCategoryResponseMapper;
import com.pashonokk.marketplace.mapper.SubCategorySavingMapper;
import com.pashonokk.marketplace.repository.CategoryRepository;
import com.pashonokk.marketplace.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryResponseMapper subCategoryResponseMapper;
    private final SubCategorySavingMapper subCategorySavingMapper;
    private final PageMapper pageMapper;
    private final CategoryRepository categoryRepository;
    private static final String SUBCATEGORY_ERROR_MESSAGE = "SubCategory with id %s doesn't exist";
    private static final String CATEGORY_ERROR_MESSAGE = "Category with id %s doesn't exist";

    @Transactional(readOnly = true)
    public SubCategoryResponseDto getSubCategory(Long id) {
        return subCategoryRepository.findById(id).map(subCategoryResponseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(SUBCATEGORY_ERROR_MESSAGE, id)));
    }

    @Transactional(readOnly = true)
    public PageResponse<SubCategoryResponseDto> getAllSubCategories(Pageable pageable) {
        return pageMapper.toPageResponse(subCategoryRepository.findAll(pageable).map(subCategoryResponseMapper::toDto));
    }

    @Transactional
    public SubCategoryResponseDto addSubCategory(SubCategorySavingDto subCategorySavingDto) {
        Category category = categoryRepository.findById(subCategorySavingDto.getCategoryId())
                .orElseThrow(
                        () -> new CategoryDoesntExistException(
                                String.format(CATEGORY_ERROR_MESSAGE, subCategorySavingDto.getCategoryId())));
        SubCategory subCategory = subCategorySavingMapper.toEntity(subCategorySavingDto);
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryResponseMapper.toDto(savedSubCategory);
    }
}
