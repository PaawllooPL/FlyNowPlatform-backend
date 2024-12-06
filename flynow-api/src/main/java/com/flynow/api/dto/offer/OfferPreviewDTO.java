package com.flynow.api.dto.offer;

import com.flynow.domain.models.offer.OfferPreview;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OfferPreviewDTO {

    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private String aircraftType;
    private String address;
    private String voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OfferPreviewDTO fromDomain(OfferPreview offerPreview) {
        return OfferPreviewDTO.builder()
                .flightId(offerPreview.getFlightId())
                .title(offerPreview.getTitle())
                .pricePerPerson(offerPreview.getPricePerPerson())
                .aircraftType(offerPreview.getAircraftType())
                .address(offerPreview.getAddress())
                .voivodeship(offerPreview.getVoivodeship())
                .flightDate(offerPreview.getFlightDate())
                .imageFilename(offerPreview.getImageFilename())
                .build();
    }
}
