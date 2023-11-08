package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.SubCategorySavingDto;
import com.pashonokk.marketplace.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCategorySavingMapper {
    SubCategory toEntity(SubCategorySavingDto subCategorySavingDto);

    SubCategorySavingDto toDto(SubCategory subCategory);

}
