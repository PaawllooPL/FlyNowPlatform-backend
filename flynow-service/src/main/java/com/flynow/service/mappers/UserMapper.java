package com.flynow.service.mappers;

import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import lombok.AllArgsConstructor;
import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
