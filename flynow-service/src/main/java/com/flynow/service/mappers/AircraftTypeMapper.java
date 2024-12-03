package com.flynow.service.mappers;

import com.flynow.domain.models.AircraftType;
import com.flynow.repository.entities.AircraftTypeEntity;

public class AircraftTypeMapper {

    public static AircraftTypeEntity toEntity(final AircraftType aircraftType) {
        return AircraftTypeEntity.of(null, aircraftType.getName());
    }

}
