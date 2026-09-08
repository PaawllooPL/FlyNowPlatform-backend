package com.flynow.service.repository.command;

import com.flynow.domain.models.Passenger;

public interface UserFlightCommandRepository {

    void save(Passenger passenger);
}
