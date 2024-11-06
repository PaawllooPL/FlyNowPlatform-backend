package com.flynow.api.dto.offer;

import com.flynow.api.dto.comment.CommentDTO;

import java.util.List;

public class OfferDetailsDTO {

    private Integer offerId;
    private String title;
    private Integer pricePerPerson;
    private Integer remainingSeats;
    private String aircraftType;
    private String imagePath;
    private String description;
    private Integer eventOrganizerId;
    private String eventOrganizerName;
    private Integer eventOrganizerRating;
    private List<CommentDTO> comments;
}
