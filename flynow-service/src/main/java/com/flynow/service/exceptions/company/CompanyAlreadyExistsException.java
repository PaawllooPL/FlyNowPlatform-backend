package com.flynow.service.exceptions.company;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException(){super("Company already exists.");}
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
