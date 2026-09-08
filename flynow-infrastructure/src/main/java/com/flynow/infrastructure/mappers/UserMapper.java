package com.flynow.infrastructure.mappers;

import com.flynow.domain.models.User;
import com.flynow.infrastructure.entities.RoleEntity;
import com.flynow.infrastructure.entities.UserEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserMapper {

    public UserEntity toEntityWithExistingRoles(User user, List<RoleEntity> roleEntities) {
        // UserEntity field order: id, username, email, passwordHash, accountRoles
        return UserEntity.of(null, user.getUsername(), user.getEmail(), user.getPasswordHash(), roleEntities);
    }
    public static User toDomain(UserEntity userEntity) {
        // User field order: id, username, email, passwordHash, roles
        return User.of(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                userEntity.getAccountRoles().stream().map(RoleMapper::toDomain).toList());
    }
}
