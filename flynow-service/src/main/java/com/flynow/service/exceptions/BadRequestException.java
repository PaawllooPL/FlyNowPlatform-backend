package com.flynow.service.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {super("Incorrect request");}
    public BadRequestException(String message) {super(message);}

}
