package com.flynow.api.controllers;

import com.flynow.service.exceptions.*;
import com.flynow.service.exceptions.flight.FlightNotFoundException;
import com.flynow.service.exceptions.comment.CommentNotAllowedException;
import com.flynow.service.exceptions.flight.SeatNotAvailableException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //GENERAL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception ex) {
        logger.error("Exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        logger.debug("Runtime exception: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadClientRequestException(BadRequestException e) {
        logger.debug("Bad client request: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    //AUTH
    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity<String> notAuthenticated(NotAuthenticatedException e) {
        logger.debug(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> notAuthorized(NotAuthorizedException e) {
        logger.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
    }
    //----------------------------NOT FOUND--------------------------------------
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException e) {
        logger.debug("User not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<String> companyNotFoundException(CompanyNotFoundException e) {
        logger.debug("Company not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found.");
    }
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> flightNotFoundException(FlightNotFoundException e) {
        logger.debug("Flight not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found.");
    }
    @ExceptionHandler(AircraftTypeNotFoundException.class)
    public ResponseEntity<String> aircraftTypeNotFoundException(AircraftTypeNotFoundException e) {
        logger.debug("Aircraft type not found. Error message:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft type not found.");
    }
    //---------------------------PERMISSION---------------------------------------
    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<String> insufficientPermissionsException(InsufficientPermissionsException e) {
        logger.debug("Insuffisient permissions. Error message:", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    //---------------------------COMMENT---------------------------------------
    @ExceptionHandler(CommentNotAllowedException.class)
    public ResponseEntity<String> commentNotAllowedException(CommentNotAllowedException e) {
        logger.debug("Comment not allowed. Error message:");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Comment not allowed.");
    }
    @ExceptionHandler(SeatNotAvailableException.class)
    public ResponseEntity<String> seatNotAvailableException(SeatNotAvailableException e) {
        logger.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.GONE).body("Seat not available");
    }
}
