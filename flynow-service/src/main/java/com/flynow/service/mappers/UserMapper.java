package com.flynow.service.mappers;

import com.flynow.domain.models.user.User;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
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
