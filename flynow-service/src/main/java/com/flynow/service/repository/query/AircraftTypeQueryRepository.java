package com.flynow.service.repository.query;

import com.flynow.domain.models.AircraftType;

public interface AircraftTypeQueryRepository {
    AircraftType findByName(String name);
}
