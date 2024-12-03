package com.flynow.service.exceptions;

public class AircraftTypeNotFoundException extends RuntimeException {

    public AircraftTypeNotFoundException() {super("Aircraft type not found");}
    public AircraftTypeNotFoundException(String message) {super(message);}
}
