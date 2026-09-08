package com.flynow.infrastructure.repository.jpa;

import com.flynow.infrastructure.entities.UserFlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFlightJpaRepository extends JpaRepository<UserFlightEntity, Integer> {
    Optional<UserFlightEntity> findByUserIdAndFlightId(Integer userId, Integer flightId);
    int countByFlightId(Integer flightId);
    boolean existsByUser_IdAndFlight_Id(Integer userId, Integer flightId);
    List<UserFlightEntity> findAllByFlight_Id(Integer flightId);
}
