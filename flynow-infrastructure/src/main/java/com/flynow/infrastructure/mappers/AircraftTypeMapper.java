package com.flynow.infrastructure.mappers;

import com.flynow.domain.models.AircraftType;
import com.flynow.infrastructure.entities.AircraftTypeEntity;

public class AircraftTypeMapper {

    public static AircraftTypeEntity toEntity(final AircraftType aircraftType) {
        return AircraftTypeEntity.of(null, aircraftType.getName());
    }

}
