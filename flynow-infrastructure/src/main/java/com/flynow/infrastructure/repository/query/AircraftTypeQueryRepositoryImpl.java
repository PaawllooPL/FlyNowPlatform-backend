package com.flynow.infrastructure.repository.query;

import com.flynow.domain.models.AircraftType;
import com.flynow.infrastructure.entities.AircraftTypeEntity;
import com.flynow.infrastructure.repository.jpa.AircraftTypeJpaRepository;
import com.flynow.service.exceptions.AircraftTypeNotFoundException;
import com.flynow.service.repository.query.AircraftTypeQueryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AircraftTypeQueryRepositoryImpl implements AircraftTypeQueryRepository {

    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;

    @Override
    public AircraftType findByName(String name) {
        Optional<AircraftTypeEntity> entity = aircraftTypeJpaRepository.findByName(name);
        return entity.map(e -> AircraftType.of(e.getId(), e.getName()))
                .orElseThrow(() -> new AircraftTypeNotFoundException("Aircraft type not found: " + name));
    }
}
