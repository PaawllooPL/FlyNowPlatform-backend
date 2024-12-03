package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.UserFlightEntity;

import java.util.Optional;

public interface UserFlightJpaRepository {
    Optional<UserFlightEntity> findByUserIdAndFlightId(Integer userId, Integer flightId);
}
