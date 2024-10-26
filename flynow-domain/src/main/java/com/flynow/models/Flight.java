package com.flynow.models;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Flight {
    private Date flightDate;
    private Duration flightDuration;
    private Integer pricePerPerson;
    private String description;
    private List<User> users;
}
