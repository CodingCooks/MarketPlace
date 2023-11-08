package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.CategoryResponseDto;
import com.pashonokk.marketplace.dto.CategorySavingDto;
import com.pashonokk.marketplace.dto.SubCategoryResponseDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.exception.BigSizeException;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponseDto>> getCategories(@RequestParam(required = false, defaultValue = "0") int page,
                                                                           @RequestParam(required = false, defaultValue = "10") int size,
                                                                           @RequestParam(required = false, defaultValue = "id") String sort) {
        if (size > 100) {
            throw new BigSizeException("You can get maximum 100 categories at one time");
        }
        PageResponse<CategoryResponseDto> allCategories = categoryService.getAllCategories(PageRequest.of(page, size, Sort.by(sort)));
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping("/{id}/sub-categories")
    public ResponseEntity<List<SubCategoryResponseDto>> getCategorySubCategories(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategorySubCategories(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody @Valid CategorySavingDto categorySavingDto, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        CategoryResponseDto savedCategory = categoryService.addCategory(categorySavingDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCategory);
    }

}
