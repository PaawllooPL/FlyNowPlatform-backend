package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class Flight {
    private Integer id;
    private LocalDateTime flightDate;
    private Integer flightDuration;
    private Integer pricePerPerson;
    private Integer totalSeats;
    private String title;
    private String description;
    private String address;
    private VoivodeshipEnum voivodeship;
    private Integer companyId;
    private Integer aircraftTypeId;
}
