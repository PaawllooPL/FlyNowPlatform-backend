package com.flynow.service.exceptions.flight;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException() {}
    public FlightNotFoundException(String message) {super(message);}

}
