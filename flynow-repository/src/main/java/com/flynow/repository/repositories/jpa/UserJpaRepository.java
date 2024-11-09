package com.flynow.repository.repositories.jpa;

import com.flynow.domain.models.RoleEnum;
import com.flynow.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserEntityByEmailAndAccountRolesExists(String email, RoleEnum role);
}