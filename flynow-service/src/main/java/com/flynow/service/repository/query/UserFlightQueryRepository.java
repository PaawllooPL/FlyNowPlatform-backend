package com.flynow.service.repository.query;

import com.flynow.domain.models.Passenger;

import java.util.Optional;

public interface UserFlightQueryRepository {
    boolean existsForFlight(Integer userId, Integer flightId);
    Integer countForFlight(Integer flightId);
    Optional<Passenger> findPassenger(Integer userId, Integer flightId);
}
