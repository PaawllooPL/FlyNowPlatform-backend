package com.flynow.infrastructure.repository.models.offer;


import com.flynow.domain.models.VoivodeshipEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OfferDetailsDbProjection {
    private Integer flightId;
    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer totalSeats;
    private Long remainingSeats;
    private String aircraftType;
    private String imageFilename;
    private Integer companyId;
    private String companyName;
    private String address;
    private VoivodeshipEnum voivodeship;
    private LocalDateTime flightDate;
}
