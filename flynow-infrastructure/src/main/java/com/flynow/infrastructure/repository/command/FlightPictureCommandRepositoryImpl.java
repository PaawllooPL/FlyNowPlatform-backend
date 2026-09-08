package com.flynow.infrastructure.repository.command;

import com.flynow.infrastructure.entities.FlightEntity;
import com.flynow.infrastructure.entities.FlightPictureEntity;
import com.flynow.infrastructure.repository.jpa.FlightJpaRepository;
import com.flynow.infrastructure.repository.jpa.FlightPictureJpaRepository;
import com.flynow.service.repository.command.FlightPictureCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightPictureCommandRepositoryImpl implements FlightPictureCommandRepository {

    private final FlightJpaRepository flightJpaRepository;
    private final FlightPictureJpaRepository flightPictureJpaRepository;

    @Override
    public void save(String pictureFileName, Integer flightId) {
        FlightEntity flightProxy = flightJpaRepository.getReferenceById(flightId);
        flightPictureJpaRepository.save(FlightPictureEntity.of(null, flightProxy, pictureFileName));
    }
}
