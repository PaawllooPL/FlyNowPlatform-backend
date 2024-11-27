package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.offer.CreateOffer;
import com.flynow.domain.models.offer.OfferDetails;
import com.flynow.domain.models.offer.OfferPreview;

import java.util.List;

public interface OfferUseCases {
    List<OfferPreview> getOfferPreviews();
    OfferDetails getOfferDetails(Integer flightId);
    void buyOffer(Integer flightId);
    void createOffer(CreateOffer createOffer);
}
