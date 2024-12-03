package com.flynow.api.dto.offer;

import com.flynow.domain.models.offer.OrganizerOfferPreview;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class OrganizerOfferPreviewDTO {
    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private Integer clientCount;
    private Integer totalSeats;
    private String address;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OrganizerOfferPreviewDTO fromDomain(OrganizerOfferPreview offer) {
        return OrganizerOfferPreviewDTO.builder()
                .flightId(offer.getFlightId())
                .title(offer.getTitle())
                .pricePerPerson(offer.getPricePerPerson())
                .clientCount(offer.getClientCount())
                .totalSeats(offer.getTotalSeats())
                .address(offer.getAddress())
                .flightDate(offer.getFlightDate())
                .imageFilename(offer.getImageFilename())
                .build();
    }
}
