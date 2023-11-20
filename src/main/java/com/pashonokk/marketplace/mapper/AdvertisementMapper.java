package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.AdvertisementDto;
import com.pashonokk.marketplace.entity.Advertisement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface AdvertisementMapper {
    AdvertisementDto toDto(Advertisement advertisement);

    Advertisement toEntity(AdvertisementDto advertisementDto);
}
