package com.flynow.service.mappers;

import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import lombok.AllArgsConstructor;

import java.util.List;
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
}
