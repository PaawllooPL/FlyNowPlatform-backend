package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.offer.*;

import java.util.List;

public interface OfferUseCases {

    List<OfferPreview> getOfferPreviews();
    List<OfferPreview> getFilteredOfferPreviews(List<String> filters);
    OfferDetails getOfferDetails(Integer flightId);
    void buyOffer(Integer flightId);
    void createOffer(CreateOffer createOffer);
    List<OfferPreview> getUserOfferPreviews();
    List<OrganizerOfferPreview> getOrganizerOfferPreviews();
    OrganizerOfferDetails getOrganizerOfferDetails(Integer flightId);
}
