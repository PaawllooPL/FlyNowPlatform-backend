package com.flynow.domain.models.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@Builder
public class OrganizerOfferPreview {

    private Integer flightId;
    private String title;
    private Integer pricePerPerson;
    private Integer clientCount;
    private Integer totalSeats;
    private String address;
    private LocalDateTime flightDate;
    private String imageFilename;
}
