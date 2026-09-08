package com.flynow.api.controllers;


import com.flynow.api.dto.offer.*;
import com.flynow.service.usecases.OfferUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + OFFERS)
public class OfferController {

    private final OfferUseCases offerUseCases;

    @GetMapping()
    public ResponseEntity<List<OfferPreviewDTO>> getOfferPreviews() {
        var dtoOffers = offerUseCases.getOfferPreviews().stream()
                .map(OfferPreviewDTO::from).toList();
        return ResponseEntity.ok(dtoOffers);
    }

    @GetMapping(OFFERS_FILTERED)
    public ResponseEntity<List<OfferPreviewDTO>> getFilteredOfferPreviews(@RequestParam List<String> voivodeships) {
        var offers = offerUseCases.getFilteredOfferPreviews(voivodeships);
        return ResponseEntity.ok(offers.stream().map(OfferPreviewDTO::from).toList());
    }

    @GetMapping(value = OFFERS_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferDetailsDTO> getOfferDetails(@PathVariable final Integer flightId) {
        var offerDetails = offerUseCases.getOfferDetails(flightId);
        return ResponseEntity.ok(OfferDetailsDTO.from(offerDetails));
    }

    @GetMapping(OFFERS_BUY)
    public ResponseEntity<String> buyOffer(@PathVariable final Integer flightId) {
        offerUseCases.buyOffer(flightId);
        return ResponseEntity.ok().body("Successfully bought offer");
    }

    @PostMapping(value = OFFERS_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createOffer(@ModelAttribute CreateOfferDTO createOfferDTO) {
        offerUseCases.createOffer(createOfferDTO.toCommand());
        return ResponseEntity.status(HttpStatus.OK).body("Successfully created offer");
    }

    @GetMapping(value = OFFERS_USER)
    public ResponseEntity<List<OfferPreviewDTO>> getUserOfferPreviews() {
        var offerPreviewsDtoList = offerUseCases.getUserOfferPreviews().stream()
                .map(OfferPreviewDTO::from).toList();
        return ResponseEntity.ok().body(offerPreviewsDtoList);
    }

    @GetMapping(value = OFFERS_ORGANIZER)
    public ResponseEntity<List<OrganizerOfferPreviewDTO>> getOrganizerOfferPreviews() {
        var organizerOfferPreviewDtoList = offerUseCases.getOrganizerOfferPreviews().stream()
                .map(OrganizerOfferPreviewDTO::from).toList();
        return ResponseEntity.ok().body(organizerOfferPreviewDtoList);
    }

    @GetMapping(value = OFFERS_ORGANIZER_DETAILS)
    public ResponseEntity<OrganizerOfferDetailsDTO> getOrganizerOfferDetails(@PathVariable final Integer flightId) {
        var organizerOfferDetails = offerUseCases.getOrganizerOfferDetails(flightId);
        return ResponseEntity.status(HttpStatus.OK).body(OrganizerOfferDetailsDTO.from(organizerOfferDetails));
    }
}
