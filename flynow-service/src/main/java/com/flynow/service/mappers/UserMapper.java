package com.flynow.service.mappers;

// COMMENTED OUT — moved to flynow-infrastructure (depends on infrastructure entities, was placed in service by mistake).
// Kept here as a placeholder so the package and historical code are not lost.
/*
import com.flynow.domain.models.User;
import com.flynow.infrastructure.entities.RoleEntity;
import com.flynow.infrastructure.entities.UserEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserMapper {

    public UserEntity toEntityWithExistingRoles(User user, List<RoleEntity> roleEntities) {

        return UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .passwordHash(user.getPasswordHash())
                .accountRoles(roleEntities)
                .build();
    }
    public static User toDomain(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .passwordHash(userEntity.getPasswordHash())
                .roles(userEntity.getAccountRoles().stream().map(RoleMapper::toDomain).toList())
                .build();
    }
}
*/
