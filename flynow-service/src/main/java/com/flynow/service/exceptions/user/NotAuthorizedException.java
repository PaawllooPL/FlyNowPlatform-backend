package com.flynow.service.exceptions.user;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {super("Unauthorized");}
    public NotAuthorizedException(String message) {super(message);}
}
