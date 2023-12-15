package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.AddressSavingDto;
import com.pashonokk.marketplace.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressSavingMapper {
    AddressSavingDto toDto(Address address);

    Address toEntity(AddressSavingDto addressSavingDto);
}
