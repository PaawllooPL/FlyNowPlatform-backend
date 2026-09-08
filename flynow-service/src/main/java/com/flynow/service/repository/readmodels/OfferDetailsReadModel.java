package com.flynow.service.repository.readmodels;

import com.flynow.service.models.query.offer.OfferComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OfferDetailsReadModel {
    private Integer flightId;
    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer totalSeats;
    private Integer remainingSeats;
    private String aircraftType;
    private String imageFilename;
    private Integer eventOrganizerId;
    private String eventOrganizerName;
    private List<OfferComment> comments;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
}
