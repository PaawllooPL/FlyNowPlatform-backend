package com.flynow.infrastructure.repository.query;

import com.flynow.domain.models.Passenger;
import com.flynow.infrastructure.repository.jpa.UserFlightJpaRepository;
import com.flynow.service.repository.query.UserFlightQueryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserFlightQueryRepositoryImpl implements UserFlightQueryRepository {

    private final UserFlightJpaRepository userFlightJpaRepository;

    @Override
    public boolean existsForFlight(Integer userId, Integer flightId) {
        return userFlightJpaRepository.existsByUser_IdAndFlight_Id(userId, flightId);
    }

    @Override
    public Integer countForFlight(Integer flightId) {
        return userFlightJpaRepository.countByFlightId(flightId);
    }

    @Override
    public Optional<Passenger> findPassenger(Integer userId, Integer flightId) {
        return userFlightJpaRepository.findByUserIdAndFlightId(userId, flightId)
                .map(uf -> Passenger.of(uf.getId(), uf.getFlight().getId(), uf.getUser().getId(), uf.getDidComment()));
    }
}
