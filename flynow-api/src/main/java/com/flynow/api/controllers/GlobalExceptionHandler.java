package com.flynow.api.controllers;

import com.flynow.service.exceptions.CompanyNotFoundException;
import com.flynow.service.exceptions.InsufficientPermissionsException;
import com.flynow.service.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //----------------------------NOT FOUND--------------------------------------
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException e) {
        logger.error("User not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<String> companyNotFoundException(CompanyNotFoundException e) {
        logger.error("Company not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    //---------------------------PERMISSION---------------------------------------
    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<String> insufficientPermissionsException(InsufficientPermissionsException e) {
        logger.error("Insuffisient permissions. Error message:", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
