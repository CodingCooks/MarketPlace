package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.SubCategoryResponseDto;
import com.pashonokk.marketplace.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCategoryResponseMapper {
    SubCategory toEntity(SubCategoryResponseDto subCategoryResponseDto);

    SubCategoryResponseDto toDto(SubCategory subCategory);

}
