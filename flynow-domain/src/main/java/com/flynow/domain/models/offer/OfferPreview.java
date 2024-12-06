package com.flynow.domain.models.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@Builder
public class OfferPreview {
    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private String aircraftType;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;
}
