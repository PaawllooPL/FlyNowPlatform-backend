package com.flynow.api.controllers;


import com.flynow.api.dto.comment.CommentDTO;
import com.flynow.api.dto.offer.CreateOfferDTO;
import com.flynow.api.dto.offer.OfferDetailsDTO;
import com.flynow.api.dto.offer.OfferPreviewDTO;
import com.flynow.domain.interfaces.usecases.OfferUseCases;
import com.flynow.domain.models.offer.CreateOffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + OFFERS)
public class OfferController {

    private final OfferUseCases offerUseCases;
    private final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseEntity<List<OfferPreviewDTO>> getOfferPreviews() {
        try {
            var dtoOffers = offerUseCases.getOfferPreviews().stream().map(offerPreview ->
                    OfferPreviewDTO.builder()
                            .flightId(offerPreview.getFlightId())
                            .title(offerPreview.getTitle())
                            .pricePerPerson(offerPreview.getPricePerPerson())
                            .aircraftType(offerPreview.getAircraftType())
                            .address(offerPreview.getAddress())
                            .imageFilename(offerPreview.getImageFilename())
                            .build())
                    .toList();
            return ResponseEntity.ok(dtoOffers);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("permitAll()")
    @GetMapping(value = OFFERS_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferDetailsDTO> getOfferDetails(@PathVariable final Integer flightId) {
        var offerDetails = offerUseCases.getOfferDetails(flightId);
        var commentsDto = offerDetails.getComments().stream()
                .map(comment -> CommentDTO.of(comment.getUserId(),comment.getUsername(),comment.getRating(), comment.getContent()))
                .toList();

        var offerDetailsDto = OfferDetailsDTO.builder()
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
                .comments(commentsDto)
                .canBuy(offerDetails.getCanBuy())
                .canComment(offerDetails.getCanComment())
                .address(offerDetails.getAddress())
                .build();
        return ResponseEntity.ok(offerDetailsDto);
    }
    @GetMapping(OFFERS_BUY)
    public ResponseEntity<String> buyOffer(@PathVariable final Integer flightId) {
        offerUseCases.buyOffer(flightId);
        return ResponseEntity.ok().body("Successfully bought offer");
    }
    @PostMapping(value = OFFERS_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createOffer(@ModelAttribute CreateOfferDTO createOfferDTO) {
        offerUseCases.createOffer(createOfferDTO.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body("Successfully created offer");
    }

}
