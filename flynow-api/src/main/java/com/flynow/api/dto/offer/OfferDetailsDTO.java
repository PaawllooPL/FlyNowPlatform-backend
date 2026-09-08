package com.flynow.api.dto.offer;

import com.flynow.api.dto.comment.CommentDTO;
import com.flynow.service.models.query.offer.OfferDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OfferDetailsDTO {

    private Integer flightId;
    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer remainingSeats;
    private String aircraftType;
    private String imageFilename;
    private Integer eventOrganizerId;
    private String eventOrganizerName;
    private Float eventOrganizerRating;
    private List<CommentDTO> comments;
    private Boolean canBuy;
    private Boolean canComment;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;

    public static OfferDetailsDTO from(OfferDetails offerDetails) {
        return new OfferDetailsDTO(
                offerDetails.getFlightId(),
                offerDetails.getTitle(),
                offerDetails.getDescription(),
                offerDetails.getPricePerPerson(),
                offerDetails.getRemainingSeats(),
                offerDetails.getAircraftType(),
                offerDetails.getImageFilename(),
                offerDetails.getEventOrganizerId(),
                offerDetails.getEventOrganizerName(),
                offerDetails.getEventOrganizerRating(),
                offerDetails.getComments().stream().map(CommentDTO::from).toList(),
                offerDetails.getCanBuy(),
                offerDetails.getCanComment(),
                offerDetails.getAddress(),
                offerDetails.getVoivodeship(),
                offerDetails.getFlightDate());
    }
}
