package com.flynow.domain.models.offer;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrganizerOfferDetails {

    private Integer flightId;
    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer remainingSeats;
    private String aircraftType;
    private String imageFilename;
    private Integer eventOrganizerId;
    private String eventOrganizerName;
    private Integer eventOrganizerRating;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private List<OfferClient> clients;
}
