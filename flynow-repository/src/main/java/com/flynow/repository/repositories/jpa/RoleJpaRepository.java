package com.flynow.repository.repositories.jpa;

import com.flynow.domain.models.RoleEnum;
import com.flynow.repository.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findAllByNameIn(final List<RoleEnum> roles);
    RoleEntity findByName(RoleEnum role);
}
