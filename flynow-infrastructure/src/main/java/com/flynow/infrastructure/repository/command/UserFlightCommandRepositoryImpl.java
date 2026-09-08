package com.flynow.infrastructure.repository.command;

import com.flynow.domain.models.Passenger;
import com.flynow.infrastructure.entities.UserFlightEntity;
import com.flynow.infrastructure.repository.jpa.FlightJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserFlightJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.repository.command.UserFlightCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFlightCommandRepositoryImpl implements UserFlightCommandRepository {

    private final UserFlightJpaRepository userFlightJpaRepository;
    private final FlightJpaRepository flightJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(Passenger passenger) {        //somewhere in future might add re-try catch loop for optimistic lock exception

        userFlightJpaRepository.save(UserFlightEntity.of(
            passenger.getId(),
            flightJpaRepository.getReferenceById(passenger.getFlightId()),
            userJpaRepository.getReferenceById(passenger.getUserId()),
            passenger.getDidComment()));
    }
}
