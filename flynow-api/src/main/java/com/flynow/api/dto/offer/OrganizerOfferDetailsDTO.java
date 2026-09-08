package com.flynow.api.dto.offer;

import com.flynow.service.models.query.offer.OrganizerOfferDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrganizerOfferDetailsDTO {
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
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private List<OfferClientDTO> clients;

    public static OrganizerOfferDetailsDTO from(OrganizerOfferDetails offerDetails) {
        return new OrganizerOfferDetailsDTO(
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
                offerDetails.getAddress(),
                offerDetails.getVoivodeship(),
                offerDetails.getFlightDate(),
                offerDetails.getClients().stream().map(OfferClientDTO::from).toList());
    }
}
