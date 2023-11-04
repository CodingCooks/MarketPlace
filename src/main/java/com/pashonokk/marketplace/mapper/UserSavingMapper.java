package com.pashonokk.marketplace.mapper;


import com.pashonokk.marketplace.dto.UserSavingDto;
import com.pashonokk.marketplace.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSavingMapper {
    User toEntity(UserSavingDto userDto);
}
