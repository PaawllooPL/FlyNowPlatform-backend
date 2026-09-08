package com.flynow.service.usecases;

import com.flynow.service.commands.CreateOfferCommand;
import com.flynow.service.models.query.offer.OfferDetails;
import com.flynow.service.models.query.offer.OfferPreview;
import com.flynow.service.models.query.offer.OrganizerOfferDetails;
import com.flynow.service.models.query.offer.OrganizerOfferPreview;

import java.util.List;

public interface OfferUseCases {

    List<OfferPreview> getOfferPreviews();
    List<OfferPreview> getFilteredOfferPreviews(List<String> filters);
    OfferDetails getOfferDetails(Integer flightId);
    void buyOffer(Integer flightId);
    void createOffer(CreateOfferCommand createOffer);
    List<OfferPreview> getUserOfferPreviews();
    List<OrganizerOfferPreview> getOrganizerOfferPreviews();
    OrganizerOfferDetails getOrganizerOfferDetails(Integer flightId);
}
