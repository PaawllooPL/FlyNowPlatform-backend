package com.flynow.service.exceptions;

public class InsufficientPermissionsException extends RuntimeException {

    public InsufficientPermissionsException() {
        super("Insufficient permissions");
    }
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
