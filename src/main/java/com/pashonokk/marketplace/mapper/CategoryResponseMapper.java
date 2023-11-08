package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.CategoryResponseDto;
import com.pashonokk.marketplace.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    Category toEntity(CategoryResponseDto categoryResponseDto);

    CategoryResponseDto toDto(Category category);

}
