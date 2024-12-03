package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.AircraftTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftTypeJpaRepository extends JpaRepository<AircraftTypeEntity, Integer> {
    Optional<AircraftTypeEntity> findByName(String name);
}
