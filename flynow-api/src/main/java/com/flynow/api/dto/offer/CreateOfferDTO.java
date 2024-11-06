package com.flynow.api.dto.offer;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CreateOfferDTO {

    private String title;
    private Integer availableSeats;
    private Integer pricePerPerson;
    private String aircraftType;
    private String description;
    private MultipartFile image;
}
