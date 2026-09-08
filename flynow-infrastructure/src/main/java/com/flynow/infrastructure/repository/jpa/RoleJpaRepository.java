package com.flynow.infrastructure.repository.jpa;

import com.flynow.domain.models.RoleEnum;
import com.flynow.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findAllByNameIn(final List<RoleEnum> roles);
    Optional<RoleEntity> findByName(RoleEnum role);
}
