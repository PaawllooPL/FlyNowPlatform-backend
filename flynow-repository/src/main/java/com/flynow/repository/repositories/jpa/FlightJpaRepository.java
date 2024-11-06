package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightJpaRepository extends JpaRepository<FlightEntity, Integer> {
//    List<FlightEntity> findAllByJunctionClients
}
