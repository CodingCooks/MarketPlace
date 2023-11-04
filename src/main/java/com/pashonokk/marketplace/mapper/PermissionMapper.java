package com.pashonokk.marketplace.mapper;


import com.pashonokk.marketplace.dto.PermissionDto;
import com.pashonokk.marketplace.entity.Permission;
import com.pashonokk.marketplace.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "roleNames", expression = "java(mapRolesToNames(permission.getRoles()))")
    PermissionDto toDto(Permission permission);

    default Set<String> mapRolesToNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
