package com.flynow.service.exceptions.user;

public class InsufficientPermissionsException extends RuntimeException {

    public InsufficientPermissionsException() {
        super("Insufficient permissions");
    }
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
