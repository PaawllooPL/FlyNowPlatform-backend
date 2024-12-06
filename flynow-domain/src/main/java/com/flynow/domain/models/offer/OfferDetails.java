package com.flynow.domain.models.offer;

import com.flynow.domain.models.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OfferDetails {

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
    private List<Comment> comments;
    private Boolean canBuy;
    private Boolean canComment;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
}
