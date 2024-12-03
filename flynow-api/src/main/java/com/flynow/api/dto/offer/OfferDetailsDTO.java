package com.flynow.api.dto.offer;

import com.flynow.api.dto.comment.CommentDTO;
import com.flynow.domain.models.Comment;
import com.flynow.domain.models.offer.OfferDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
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
    private Integer eventOrganizerRating;
    private List<CommentDTO> comments;
    private Boolean canBuy;
    private Boolean canComment;
    private String address;
    private LocalDateTime flightDate;

    public static OfferDetailsDTO fromDomain(OfferDetails offerDetails, List<Comment> comments) {

        var commentsDto = comments.stream().map(CommentDTO::fromDomain).toList();
        return OfferDetailsDTO.builder()
                .flightId(offerDetails.getFlightId())
                .title(offerDetails.getTitle())
                .description(offerDetails.getDescription())
                .pricePerPerson(offerDetails.getPricePerPerson())
                .remainingSeats(offerDetails.getRemainingSeats())
                .aircraftType(offerDetails.getAircraftType())
                .imageFilename(offerDetails.getImageFilename())
                .eventOrganizerId(offerDetails.getEventOrganizerId())
                .eventOrganizerName(offerDetails.getEventOrganizerName())
                .eventOrganizerRating(offerDetails.getEventOrganizerRating())
                .comments(commentsDto)
                .canBuy(offerDetails.getCanBuy())
                .canComment(offerDetails.getCanComment())
                .address(offerDetails.getAddress())
                .flightDate(offerDetails.getFlightDate())
                .build();
    }
}
