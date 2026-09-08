package com.flynow.infrastructure.repository.jpa;

import com.flynow.infrastructure.entities.AircraftTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftTypeJpaRepository extends JpaRepository<AircraftTypeEntity, Integer> {
    Optional<AircraftTypeEntity> findByName(String name);
}
