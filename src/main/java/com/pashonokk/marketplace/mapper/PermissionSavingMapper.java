package com.pashonokk.marketplace.mapper;


import com.pashonokk.marketplace.dto.PermissionSavingDto;
import com.pashonokk.marketplace.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionSavingMapper {
    Permission toEntity(PermissionSavingDto permissionSavingDto);
}
