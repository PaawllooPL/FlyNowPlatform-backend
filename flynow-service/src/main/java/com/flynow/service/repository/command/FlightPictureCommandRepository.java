package com.flynow.service.repository.command;

public interface FlightPictureCommandRepository {
    void save(String pictureFileName, Integer flightId);
}
