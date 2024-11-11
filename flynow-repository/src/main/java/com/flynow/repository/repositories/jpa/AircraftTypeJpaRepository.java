package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.AircraftTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftTypeJpaRepository extends JpaRepository<AircraftTypeEntity, Integer> {

}
