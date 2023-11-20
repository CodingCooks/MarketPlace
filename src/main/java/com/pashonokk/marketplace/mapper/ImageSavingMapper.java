package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.ImageSavingDto;
import com.pashonokk.marketplace.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageSavingMapper {
    ImageSavingDto toDto(Image image);

    Image toEntity(ImageSavingDto imageSavingDto);
}
