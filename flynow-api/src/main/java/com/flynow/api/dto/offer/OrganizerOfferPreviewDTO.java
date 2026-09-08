package com.flynow.api.dto.offer;

import com.flynow.service.models.query.offer.OrganizerOfferPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrganizerOfferPreviewDTO {
    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private Integer clientCount;
    private Integer totalSeats;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OrganizerOfferPreviewDTO from(OrganizerOfferPreview offer) {
        return new OrganizerOfferPreviewDTO(
                offer.getFlightId(),
                offer.getTitle(),
                offer.getPricePerPerson(),
                offer.getClientCount(),
                offer.getTotalSeats(),
                offer.getAddress(),
                offer.getVoivodeship(),
                offer.getFlightDate(),
                offer.getImageFilename());
    }
}
