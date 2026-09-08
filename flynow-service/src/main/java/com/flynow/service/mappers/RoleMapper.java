package com.flynow.service.mappers;

// COMMENTED OUT — moved to flynow-infrastructure (depends on infrastructure entities, was placed in service by mistake).
// Kept here as a placeholder so the package and historical code are not lost.
/*
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
*/
