package com.flynow.infrastructure.mappers;

import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.infrastructure.entities.RoleEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleMapper {
    public static RoleEntity toEntity(RoleEnum roleEnum) {
        return RoleEntity.of(null, roleEnum);
    }
    public static RoleEntity toEntity(Role role) {
        return RoleEntity.of(null, role.getName());
    }
    public static RoleEnum toDomain(RoleEntity roleEntity) {
        return RoleEnum.valueOf(roleEntity.getName().toString());
    }
}
