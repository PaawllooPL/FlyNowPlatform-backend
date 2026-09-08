package com.flynow.service.repository.query;

import com.flynow.domain.models.Flight;
import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.service.models.query.offer.OfferPreview;
import com.flynow.service.models.query.offer.OrganizerOfferDetails;
import com.flynow.service.models.query.offer.OrganizerOfferPreview;
import com.flynow.service.repository.readmodels.OfferDetailsReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightQueryRepository {

    Optional<OfferDetailsReadModel> findOfferDetailsById(Integer offerId);
    List<OfferPreview> findAllActiveWithSeatsLeft();
    List<OfferPreview> findAllClientActiveOffers(Integer clientId, LocalDateTime afterDate);
    List<OrganizerOfferPreview> findAllOrganizerActiveOffers(Integer organizerId, LocalDateTime afterDate);
    List<OfferPreview> findAllByVoivodeshipIn(List<VoivodeshipEnum> voivodeships);
    boolean existsById(Integer flightId);
    Optional<Flight> findById(Integer flightId);
    Optional<OrganizerOfferDetails> findOrganizerOfferDetailsById(Integer flightId, Integer organizerId);
}
