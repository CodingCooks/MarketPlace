package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.SubCategoryResponseDto;
import com.pashonokk.marketplace.dto.SubCategorySavingDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.exception.BigSizeException;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.SubCategoryService;
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

@RestController
@RequestMapping("api/v1/sub-categories")
@RequiredArgsConstructor
public class SubCategoryRestController {
    private final SubCategoryService subCategoryService;
    private final Logger logger = LoggerFactory.getLogger(SubCategoryRestController.class);

    @GetMapping
    public ResponseEntity<PageResponse<SubCategoryResponseDto>> getSubCategories(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                                                 @RequestParam(required = false, defaultValue = "id") String sort) {
        if (size > 100) {
            throw new BigSizeException("You can get maximum 100 SubCategories at one time");
        }
        PageResponse<SubCategoryResponseDto> allSubCategories = subCategoryService.getAllSubCategories(PageRequest.of(page, size, Sort.by(sort)));
        return ResponseEntity.ok(allSubCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponseDto> getSubCategory(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.getSubCategory(id));
    }

    @PostMapping
    public ResponseEntity<SubCategoryResponseDto> addSubCategory(@RequestBody @Valid SubCategorySavingDto subCategorySavingDto, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        SubCategoryResponseDto savedSubCategory = subCategoryService.addSubCategory(subCategorySavingDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSubCategory.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedSubCategory);
    }
}
