package com.flynow.service.exceptions.company;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {super("Company not found");}
    public CompanyNotFoundException(String message) {super(message);}
}
