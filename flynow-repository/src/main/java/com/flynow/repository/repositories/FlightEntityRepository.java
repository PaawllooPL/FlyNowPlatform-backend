package com.flynow.repository.repositories;

import com.flynow.repository.entities.FlightEntity;
import com.flynow.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightEntityRepository extends JpaRepository<FlightEntity, Integer> {
//    List<FlightEntity> findAllByJunctionClients
}
