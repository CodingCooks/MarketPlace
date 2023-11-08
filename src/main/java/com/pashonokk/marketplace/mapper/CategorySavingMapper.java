package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.CategorySavingDto;
import com.pashonokk.marketplace.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorySavingMapper {
    Category toEntity(CategorySavingDto categorySavingDto);

    CategorySavingDto toDto(Category category);
}
