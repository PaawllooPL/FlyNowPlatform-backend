package com.flynow.infrastructure.repository.models.offer;

import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.service.models.query.offer.OfferPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OfferPreviewDbProjection {
    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private String aircraftType;
    private String address;
    private VoivodeshipEnum voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OfferPreview toOfferPreview(OfferPreviewDbProjection op) {
        return OfferPreview.of(
                op.getFlightId(),
                op.getTitle(),
                op.getPricePerPerson(),
                op.getAircraftType().toLowerCase(),
                op.getAddress(),
                op.getVoivodeship().name(),
                op.getFlightDate(),
                op.getImageFilename());
    }
}
