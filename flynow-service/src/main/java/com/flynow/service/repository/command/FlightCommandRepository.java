package com.flynow.service.repository.command;

import com.flynow.domain.models.Flight;

public interface FlightCommandRepository {
    Integer save(Flight flight);
}
