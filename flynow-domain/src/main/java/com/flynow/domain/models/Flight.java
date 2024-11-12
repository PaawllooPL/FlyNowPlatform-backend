package com.flynow.domain.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Flight {
    private LocalDateTime flightDate;
    private Integer flightDuration;
    private Integer pricePerPerson;
    private String description;
    private List<User> users;
}
