package com.flynow.api.dto.offer;

import com.flynow.domain.models.offer.OrganizerOfferDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
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
    private Integer eventOrganizerRating;
    private String address;
    private LocalDateTime flightDate;
    private List<OfferClientDTO> clients;

    public static OrganizerOfferDetailsDTO fromDomain(OrganizerOfferDetails offerDetails) {

        var clientsDto = offerDetails.getClients().stream().map(OfferClientDTO::fromDomain).toList();

        return OrganizerOfferDetailsDTO.builder()
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
                .address(offerDetails.getAddress())
                .flightDate(offerDetails.getFlightDate())
                .clients(clientsDto)
                .build();
    }
}
