package com.flynow.service.exceptions.user;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException() {super("Not authenticated");}
    public NotAuthenticatedException(String message) {super(message);}
}
