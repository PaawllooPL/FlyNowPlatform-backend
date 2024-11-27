package com.flynow.service.exceptions.flight;

public class SeatNotAvailableException extends RuntimeException {
    public SeatNotAvailableException() {super("Seat not available");}
    public SeatNotAvailableException(String message) {super(message);}
}
