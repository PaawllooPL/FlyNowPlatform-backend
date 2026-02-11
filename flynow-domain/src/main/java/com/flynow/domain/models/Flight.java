package com.flynow.domain.models;

import com.flynow.domain.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    private LocalDateTime flightDate;
    private Integer flightDuration;
    private Integer pricePerPerson;
    private String description;
    private List<User> users;
}
