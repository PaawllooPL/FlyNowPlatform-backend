package com.flynow.service.services;

import com.flynow.domain.models.Flight;
import com.flynow.domain.models.Passenger;
import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.service.commands.CreateOfferCommand;
import com.flynow.service.exceptions.company.CompanyNotFoundException;
import com.flynow.service.exceptions.flight.FlightNotFoundException;
import com.flynow.service.exceptions.flight.SeatNotAvailableException;
import com.flynow.service.exceptions.user.NotAuthenticatedException;
import com.flynow.service.exceptions.user.NotAuthorizedException;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.models.query.offer.*;
import com.flynow.service.repository.command.FlightCommandRepository;
import com.flynow.service.repository.command.FlightPictureCommandRepository;
import com.flynow.service.repository.command.UserFlightCommandRepository;
import com.flynow.service.repository.query.AircraftTypeQueryRepository;
import com.flynow.service.repository.query.CompanyQueryRepository;
import com.flynow.service.repository.query.FlightQueryRepository;
import com.flynow.service.repository.query.UserFlightQueryRepository;
import com.flynow.service.usecases.CommentUseCases;
import com.flynow.service.usecases.OfferUseCases;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OfferServiceImpl implements OfferUseCases {

    private final FlightQueryRepository flightQueryRepository;
    private final UserFlightQueryRepository userFlightQueryRepository;
    private final UserFlightCommandRepository userFlightCommandRepository;
    private final CompanyQueryRepository companyQueryRepository;
    private final FlightCommandRepository flightCommandRepository;
    private final FlightPictureCommandRepository flightPictureCommandRepository;
    private final AircraftTypeQueryRepository aircraftTypeQueryRepository;
    private final CommentUseCases commentUseCases;
    private final ImageService imageService;

    private Logger logger = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Override
    public List<OfferPreview> getOfferPreviews() {
        return flightQueryRepository.findAllActiveWithSeatsLeft();
    }

    @Override
    public List<OfferPreview> getFilteredOfferPreviews(List<String> filters) {

        if(filters.isEmpty())
            return flightQueryRepository.findAllActiveWithSeatsLeft();

        var filterEnums = filters.stream().map(VoivodeshipEnum::valueOf).toList();
        return flightQueryRepository.findAllByVoivodeshipIn(filterEnums);
    }

    @Override
    public OfferDetails getOfferDetails(Integer flightId) throws RuntimeException {

        var offerDetailsReadModel = flightQueryRepository.findOfferDetailsById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(String.format("Flight with id: %d not found", flightId)));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetailsImpl;
        Integer userId = isAuthenticated ?
                            ((UserDetailsImpl) authentication.getPrincipal()).getId() :
                            null;
        boolean isPastFlight = LocalDateTime.now().isAfter(offerDetailsReadModel.getFlightDate());
        boolean isPassenger = userId != null && userFlightQueryRepository.existsForFlight(userId, flightId);
        int seatsLeft = offerDetailsReadModel.getRemainingSeats();
        boolean anySeatsLeft = seatsLeft > 0;

        if(isPastFlight && !isPassenger)
            throw new NotAuthorizedException("Only flight passenger can see archive offers");

        boolean canBuy = isAuthenticated && !isPastFlight && !isPassenger && anySeatsLeft;

        logger.debug("Can buy: {}", canBuy);
        logger.debug("Did buy: {}", isPassenger);

        return OfferDetails.builder()
                .flightId(offerDetailsReadModel.getFlightId())
                .title(offerDetailsReadModel.getTitle())
                .description(offerDetailsReadModel.getDescription())
                .pricePerPerson(offerDetailsReadModel.getPricePerPerson())
                .remainingSeats(seatsLeft)
                .aircraftType(offerDetailsReadModel.getAircraftType())
                .imageFilename(offerDetailsReadModel.getImageFilename())
                .eventOrganizerId(offerDetailsReadModel.getEventOrganizerId())
                .eventOrganizerName(offerDetailsReadModel.getEventOrganizerName())
                .eventOrganizerRating(companyRatingFromComments(offerDetailsReadModel.getComments()))
                .comments(offerDetailsReadModel.getComments())
                .canBuy(canBuy)
                .canComment(commentUseCases.canComment(flightId))
                .address(offerDetailsReadModel.getAddress())
                .voivodeship(offerDetailsReadModel.getVoivodeship())
                .flightDate(offerDetailsReadModel.getFlightDate())
                .build();
    }

    @Override
    public void buyOffer(Integer flightId) throws RuntimeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new NotAuthenticatedException("Not authenticated user tried to buy offer");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Flight flight = flightQueryRepository.findById(flightId).orElseThrow(
                () -> new FlightNotFoundException("Flight with id: " + flightId + " not found"));

        if(companyQueryRepository.existsByIdAndOrganizerAccountId(flight.getCompanyId(), userDetails.getId()))
            throw new NotAuthorizedException("Organizer can not buy their own offer.");

        if(userFlightQueryRepository.countForFlight(flightId) >= flight.getTotalSeats())
            throw new SeatNotAvailableException("No Seats on flight id: " + flightId + " available");

        if(userFlightQueryRepository.existsForFlight(userDetails.getId(), flightId))
            throw new NotAuthorizedException("User with id: " + userDetails.getId() + " already bought flight seat");

        Passenger passenger = Passenger.of(null, flightId, userDetails.getId(), false);
        userFlightCommandRepository.save(passenger);
    }

    @Override
    public void createOffer(CreateOfferCommand createOffer) {
        // Organizer/admin role is enforced at the API layer by SecurityConfig
        // (OFFERS_CREATE_URL -> hasAnyAuthority(organizer, admin)). No in-service check needed.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
            throw new NotAuthenticatedException("Not authenticated user tried to create offer");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var company = companyQueryRepository.findByOrganizerId(userDetails.getId())
                .orElseThrow(() -> new CompanyNotFoundException(
                        "Company not found for organizer id: " + userDetails.getId()));
        var aircraftType = aircraftTypeQueryRepository.findByName(createOffer.getAircraftType());
        var pictureFileName = imageService.saveImageToStorage(createOffer.getOriginalPictureFilename(), createOffer.getPictureBytes())
                .orElseThrow(() -> new RuntimeException(
                        "Could not save image to storage: " + createOffer.getOriginalPictureFilename()));

        VoivodeshipEnum voivodeship = VoivodeshipEnum.valueOf(createOffer.getVoivodeship());

        Flight flight = Flight.of(null, createOffer.getFlightDate(), createOffer.getDuration(),
                createOffer.getPricePerPerson(), createOffer.getTotalSeats(), createOffer.getTitle(),
                createOffer.getDescription(), createOffer.getAddress(), voivodeship, company.getId(),
                aircraftType.getId());

        Integer savedFlightId = flightCommandRepository.save(flight);
        flightPictureCommandRepository.save(pictureFileName, savedFlightId);
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

    @Override
    public List<OfferPreview> getUserOfferPreviews() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get user offer previews. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return flightQueryRepository.findAllClientActiveOffers(
                userDetails.getId(), LocalDateTime.now().minusWeeks(2));
    }

    @Override
    public List<OrganizerOfferPreview> getOrganizerOfferPreviews() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get organizer offer previews. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return flightQueryRepository.findAllOrganizerActiveOffers(
                userDetails.getId(), LocalDateTime.now().minusWeeks(2));
    }

    @Override
    public OrganizerOfferDetails getOrganizerOfferDetails(Integer flightId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get organizer offer details. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!flightQueryRepository.existsById(flightId))
            throw new FlightNotFoundException("Flight with id: " + flightId + " not found");

        return flightQueryRepository.findOrganizerOfferDetailsById(flightId, userDetails.getId())
                .orElseThrow(() -> new NotAuthorizedException(
                        "User id: " + userDetails.getId() + " is not the organizer of flight id: " + flightId));
    }
}
