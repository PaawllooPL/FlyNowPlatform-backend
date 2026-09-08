package com.flynow.infrastructure.repository.jpa;

import com.flynow.infrastructure.entities.FlightPictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightPictureJpaRepository extends JpaRepository<FlightPictureEntity, Integer> {
    Optional<FlightPictureEntity> findByFlight_Id(Integer flightId);
}
