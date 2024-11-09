package com.flynow.api.controllers;

import com.flynow.service.exceptions.InsufficientPermissionsException;
import com.flynow.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<String> insufficientPermissionsException(InsufficientPermissionsException e) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("TESTOWA DUPA BRAK PERMISJI 403");
    }
}
