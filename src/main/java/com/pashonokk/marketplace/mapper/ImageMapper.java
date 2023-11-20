package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.ImageDto;
import com.pashonokk.marketplace.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);

    Image toEntity(ImageDto imageDto);
}
