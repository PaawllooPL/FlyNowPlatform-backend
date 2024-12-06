package com.flynow.domain.models.offer;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateOffer {
    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer totalSeats;
    private LocalDateTime flightDate;
    private Integer duration;
    private String aircraftType;
    private String address;
    private String originalPictureFilename;
    private String voivodeship;
    private byte[] pictureBytes;
}
