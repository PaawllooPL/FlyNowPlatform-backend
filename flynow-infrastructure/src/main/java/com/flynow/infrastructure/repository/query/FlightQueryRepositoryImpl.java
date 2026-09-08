package com.flynow.infrastructure.repository.query;

import com.flynow.domain.models.Flight;
import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.infrastructure.repository.jpa.CommentJpaRepository;
import com.flynow.infrastructure.repository.jpa.FlightJpaRepository;
import com.flynow.infrastructure.repository.jpa.FlightPictureJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserFlightJpaRepository;
import com.flynow.infrastructure.repository.models.offer.OfferPreviewDbProjection;
import com.flynow.infrastructure.repository.models.offer.OrganizerOfferPreviewDbProjection;
import com.flynow.service.models.query.offer.*;
import com.flynow.service.repository.query.FlightQueryRepository;
import com.flynow.service.repository.readmodels.OfferDetailsReadModel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FlightQueryRepositoryImpl implements FlightQueryRepository {

    private final FlightJpaRepository flightJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final UserFlightJpaRepository userFlightJpaRepository;
    private final FlightPictureJpaRepository flightPictureJpaRepository;

    @Override
    public Optional<OfferDetailsReadModel> findOfferDetailsById(Integer flightId) {

        var offerDetailsProjOpt = flightJpaRepository.findOfferDetailsProjById(flightId);

        if(offerDetailsProjOpt.isEmpty())
            return Optional.empty();

        var offerDetailsProj = offerDetailsProjOpt.get();

        var offerComments = commentJpaRepository.findAllOfferCommentForCompany(offerDetailsProj.getCompanyId());

        return Optional.of(OfferDetailsReadModel.builder()
                .flightId(flightId)
                .title(offerDetailsProj.getTitle())
                .description(offerDetailsProj.getDescription())
                .pricePerPerson(offerDetailsProj.getPricePerPerson())
                .totalSeats(offerDetailsProj.getTotalSeats())
                .remainingSeats(offerDetailsProj.getRemainingSeats().intValue())
                .aircraftType(offerDetailsProj.getAircraftType())
                .imageFilename(offerDetailsProj.getImageFilename())
                .eventOrganizerId(offerDetailsProj.getCompanyId())
                .eventOrganizerName(offerDetailsProj.getCompanyName())
                .comments(offerComments)
                .address(offerDetailsProj.getAddress())
                .voivodeship(offerDetailsProj.getVoivodeship().name())
                .flightDate(offerDetailsProj.getFlightDate())
                .build());
    }

    @Override
    public List<OfferPreview> findAllActiveWithSeatsLeft() {
        var offersProj = flightJpaRepository.findAllActiveWithSeatsLeft();
        return offersProj.stream().map(OfferPreviewDbProjection::toOfferPreview).toList();
    }

    @Override
    public List<OfferPreview> findAllClientActiveOffers(Integer clientId, LocalDateTime afterDate) {
        var offersProj = flightJpaRepository.findAllClientActiveOffers(clientId, afterDate);
        return offersProj.stream().map(OfferPreviewDbProjection::toOfferPreview).toList();
    }

    @Override
    public List<OrganizerOfferPreview> findAllOrganizerActiveOffers(Integer organizerId, LocalDateTime afterDate) {
        var orgOffersProj = flightJpaRepository.findAllOrganizerActiveOffers(organizerId, afterDate);
        return orgOffersProj.stream().map(OrganizerOfferPreviewDbProjection::toOrgOfferPreview).toList();
    }

    @Override
    public List<OfferPreview> findAllByVoivodeshipIn(List<VoivodeshipEnum> voivodeships) {
        var offersProj = flightJpaRepository.findAllByVoivodeshipIn(voivodeships);
        return offersProj.stream().map(OfferPreviewDbProjection::toOfferPreview).toList();
    }

    @Override
    public boolean existsById(Integer flightId) {
        return flightJpaRepository.existsById(flightId);
    }

    @Override
    public Optional<Flight> findById(Integer flightId) {

        var flightEntityOpt = flightJpaRepository.findById(flightId);
        if(flightEntityOpt.isEmpty())
            return Optional.empty();

        var flightEntity = flightEntityOpt.get();
        return Optional.of(
                Flight.of(
                flightEntity.getId(),
                flightEntity.getFlightDate(),
                flightEntity.getDuration(),
                flightEntity.getPricePerPerson(),
                flightEntity.getTotalSeats(),
                flightEntity.getTitle(),
                flightEntity.getDescription(),
                flightEntity.getAddress(),
                flightEntity.getVoivodeship(),
                flightEntity.getCompany().getId(),
                flightEntity.getAircraftType().getId()
        ));
    }

    @Override
    public Optional<OrganizerOfferDetails> findOrganizerOfferDetailsById(Integer flightId, Integer organizerId) {

        var flightEntityOpt = flightJpaRepository.findById(flightId);
        if(flightEntityOpt.isEmpty())
            return Optional.empty();

        var flightEntity = flightEntityOpt.get();

        if(!flightEntity.getCompany().getOrganizerAccount().getId().equals(organizerId))
            return Optional.empty();

        var userFlights = userFlightJpaRepository.findAllByFlight_Id(flightId);
        var clients = userFlights.stream()
                .map(uf -> OfferClient.of(uf.getUser().getEmail(), uf.getUser().getUsername()))
                .toList();

        var comments = commentJpaRepository.findAllOfferCommentForCompany(flightEntity.getCompany().getId());
        Float rating = companyRatingFromComments(comments);

        var remainingSeats = flightEntity.getTotalSeats() - userFlights.size();

        var imageFilename = flightPictureJpaRepository.findByFlight_Id(flightId)
                .map(fp -> fp.getPictureFileName())
                .orElse(null);

        return Optional.of(OrganizerOfferDetails.builder()
                .flightId(flightEntity.getId())
                .title(flightEntity.getTitle())
                .description(flightEntity.getDescription())
                .pricePerPerson(flightEntity.getPricePerPerson())
                .remainingSeats(remainingSeats)
                .aircraftType(flightEntity.getAircraftType().getName())
                .imageFilename(imageFilename)
                .eventOrganizerId(organizerId)
                .eventOrganizerName(flightEntity.getCompany().getName())
                .eventOrganizerRating(rating)
                .address(flightEntity.getAddress())
                .voivodeship(flightEntity.getVoivodeship().toString())
                .flightDate(flightEntity.getFlightDate())
                .clients(clients)
                .build());
    }

    private Float companyRatingFromComments(List<OfferComment> comments) {
        if(comments == null || comments.isEmpty())
            return null;
        var ratingSum = comments.stream()
                .map(OfferComment::getRating)
                .mapToInt(Integer::intValue)
                .sum();
        if(ratingSum <= 0)
            return null;
        Float average = (float) ratingSum / comments.size();
        return new BigDecimal(average).setScale(2, RoundingMode.HALF_UP).floatValue();
    }
}
