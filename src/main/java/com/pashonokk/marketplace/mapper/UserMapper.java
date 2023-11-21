package com.pashonokk.marketplace.mapper;

import com.pashonokk.marketplace.dto.UserDto;
import com.pashonokk.marketplace.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
