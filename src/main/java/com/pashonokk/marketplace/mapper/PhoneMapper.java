package com.pashonokk.marketplace.mapper;



import com.pashonokk.marketplace.dto.PhoneDto;
import com.pashonokk.marketplace.entity.Phone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    PhoneDto toDto(Phone phone);
}
