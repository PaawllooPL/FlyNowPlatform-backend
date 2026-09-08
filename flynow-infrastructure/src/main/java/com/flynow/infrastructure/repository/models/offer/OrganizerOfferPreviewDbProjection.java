package com.flynow.infrastructure.repository.models.offer;

import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.service.models.query.offer.OrganizerOfferPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrganizerOfferPreviewDbProjection {
    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private Long clientCount;
    private Integer totalSeats;
    private String address;
    private VoivodeshipEnum voivodeship;
    private LocalDateTime flightDate;
    private String imageFilename;

    public static OrganizerOfferPreview toOrgOfferPreview(OrganizerOfferPreviewDbProjection oop) {
        return OrganizerOfferPreview.of(
                oop.flightId,
                oop.title,
                oop.pricePerPerson,
                oop.clientCount.intValue(),
                oop.totalSeats,
                oop.address,
                oop.voivodeship.name(),
                oop.flightDate,
                oop.imageFilename
        );
    }
}
