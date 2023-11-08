package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.CategoryRequestDto;
import com.pashonokk.marketplace.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryRequestDto toDto(Category category);

}
