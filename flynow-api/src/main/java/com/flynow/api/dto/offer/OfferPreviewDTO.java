package com.flynow.api.dto.offer;

import com.flynow.service.models.query.offer.OfferPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OfferPreviewDTO {

    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private String aircraftType;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OfferPreviewDTO from(OfferPreview offerPreview) {
        return new OfferPreviewDTO(
                offerPreview.getFlightId(),
                offerPreview.getTitle(),
                offerPreview.getPricePerPerson(),
                offerPreview.getAircraftType(),
                offerPreview.getAddress(),
                offerPreview.getVoivodeship(),
                offerPreview.getFlightDate(),
                offerPreview.getImageFilename());
    }
}
