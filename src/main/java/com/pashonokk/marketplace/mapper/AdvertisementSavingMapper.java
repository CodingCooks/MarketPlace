package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.AdvertisementSavingDto;
import com.pashonokk.marketplace.entity.Advertisement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ImageSavingMapper.class)
public interface AdvertisementSavingMapper {
    AdvertisementSavingDto toDto(Advertisement advertisement);

    Advertisement toEntity(AdvertisementSavingDto advertisementSavingDto);
}
