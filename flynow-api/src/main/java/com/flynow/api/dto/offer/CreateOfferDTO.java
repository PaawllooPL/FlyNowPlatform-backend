package com.flynow.api.dto.offer;

import com.flynow.service.commands.CreateOfferCommand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateOfferDTO {

    private String title;
    private String description;
    private Integer pricePerPerson;
    private Integer totalSeats;
    private LocalDateTime flightDate;
    private Integer duration;
    private String aircraftType;
    private String address;
    private String voivodeship;
    private MultipartFile image;

    public CreateOfferCommand toCommand() {
        try {
            return CreateOfferCommand.builder()
                .title(title)
                .description(description)
                .pricePerPerson(pricePerPerson)
                .totalSeats(totalSeats)
                .flightDate(flightDate)
                .duration(duration)
                .aircraftType(aircraftType)
                .address(address)
                .voivodeship(voivodeship)
                .originalPictureFilename(image.getOriginalFilename())
                .pictureBytes(image.getBytes())
                .build();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
