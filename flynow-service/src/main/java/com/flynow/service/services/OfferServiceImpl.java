package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CommentUseCases;
import com.flynow.domain.interfaces.usecases.OfferUseCases;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.offer.*;
import com.flynow.repository.entities.*;
import com.flynow.repository.repositories.jpa.AircraftTypeJpaRepository;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.FlightJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.*;
import com.flynow.service.exceptions.flight.FlightNotFoundException;
import com.flynow.service.exceptions.flight.SeatNotAvailableException;
import com.flynow.service.mappers.CommentMapper;
import com.flynow.service.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OfferServiceImpl implements OfferUseCases {

    private final FlightJpaRepository flightJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;
    private final CommentUseCases commentUseCases;
    private final ImageService imageService;

    private Logger logger = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Override
    @Transactional
    public List<OfferPreview> getOfferPreviews() {
        var flights = flightJpaRepository.findAllActiveWithSeatsLeft();

        return flights.stream()
                .map(flightEntity -> OfferPreview.builder()
                        .flightId(flightEntity.getId())
                        .title(flightEntity.getTitle())
                        .pricePerPerson(flightEntity.getPricePerPerson())
                        .aircraftType(flightEntity.getAircraftType().getName())
                        .address(flightEntity.getAddress())
                        .flightDate(flightEntity.getFlightDate())
                        .imageFilename(flightEntity.getFlightPicture().getPictureFileName())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public OfferDetails getOfferDetails(Integer flightId) throws RuntimeException {
        FlightEntity flightEntity = flightJpaRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(String.format("Flight with id: %d not found", flightId)));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean canBuy;

        if(LocalDateTime.now().isAfter(flightEntity.getFlightDate())) {
            canBuy = false;
            if(authentication == null)
                throw new NotAuthenticatedException();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            flightEntity.getJunctionClients().stream()
                    .filter(jc -> jc.getUser().getId() == userDetails.getUserEntity().getId())
                    .findFirst().orElseThrow(() -> new NotAuthorizedException("User not assigned to flight with id: " + flightId));
        }
        if(authentication == null) {
            canBuy = false;
        }
        else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            var didBuy = flightEntity.getJunctionClients().stream().anyMatch(jc -> jc.getUser().getId() == userDetails.getUserEntity().getId());
            canBuy = !didBuy;
            if(flightEntity.getCompany().getOrganizerAccount().getId() == userDetails.getUserEntity().getId())
                canBuy = false;
            logger.debug("Can buy: {}", canBuy);
            logger.debug("Did buy: {}", didBuy);
        }

        var remainingSeats = flightEntity.getTotalSeats() - flightEntity.getJunctionClients().size();
        logger.debug("Can buy one more time: {}", canBuy);
        return OfferDetails.builder()
                .flightId(flightEntity.getId())
                .title(flightEntity.getTitle())
                .description(flightEntity.getDescription())
                .pricePerPerson(flightEntity.getPricePerPerson())
                .remainingSeats(remainingSeats)
                .aircraftType(flightEntity.getAircraftType().getName())
                .imageFilename(flightEntity.getFlightPicture().getPictureFileName())
                .eventOrganizerId(flightEntity.getCompany().getId())
                .eventOrganizerName(flightEntity.getCompany().getName())
                .eventOrganizerRating(companyRating(flightEntity))
                .comments(flightEntity.getCompany().getComments().stream().map(CommentMapper::toDomain).toList())
                .canBuy(canBuy)
                .canComment(commentUseCases.canComment(flightId))
                .address(flightEntity.getAddress())
                .flightDate(flightEntity.getFlightDate())
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void buyOffer(Integer flightId) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null){
            throw new NotAuthenticatedException("Not authenticated user tried to buy offer");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserEntity user = userJpaRepository.findById(userDetails.getUserEntity().getId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userDetails.getUserEntity().getId() + " not found"));

        FlightEntity flight = flightJpaRepository.findById(flightId).orElseThrow(
                () -> new FlightNotFoundException("Flight with id: " + flightId + " not found"));

        if(user.getId() == flight.getCompany().getOrganizerAccount().getId())
            throw new NotAuthorizedException("User can not buy their offer.");

        if(flight.getJunctionClients().size() >= flight.getTotalSeats())
            throw new SeatNotAvailableException("Seat on flight id: " + flightId + " not available");

        if(flight.getJunctionClients().stream().anyMatch(jc -> jc.getUser().getId() == userDetails.getUserEntity().getId()))
            throw new NotAuthorizedException("User with id: " + userDetails.getUserEntity().getId() + " already bought flight seat");

        flight.getJunctionClients().add(UserFlightEntity.of(null, user, false));
        flightJpaRepository.save(flight);
    }

    @Override
    public void createOffer(CreateOffer createOffer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userDetails.getAuthorities().stream().filter(a -> a.getAuthority().equals(RoleEnum.organizer.name()))
                .findFirst().orElseThrow(() -> new NotAuthorizedException("User id: "
                        + userDetails.getUserEntity().getId() +
                        "missing organizer role"));

        var company = companyJpaRepository.findByOrganizerAccountId(userDetails.getUserEntity().getId())
                .orElseThrow(CompanyNotFoundException::new);
        var aircraftType = aircraftTypeJpaRepository.findByName(createOffer.getAircraftType())
                .orElseThrow(AircraftTypeNotFoundException::new);
        var pictureFileName = imageService.saveImageToStorage(createOffer.getOriginalPictureFilename(), createOffer.getPictureBytes())
                .orElseThrow(() -> new RuntimeException("Could not save image to storage"));

        FlightEntity newFlight = FlightEntity.builder()
                .id(null)
                .flightDate(createOffer.getFlightDate())
                .duration(createOffer.getDuration())
                .pricePerPerson(createOffer.getPricePerPerson())
                .totalSeats(createOffer.getTotalSeats())
                .title(createOffer.getTitle())
                .description(createOffer.getDescription())
                .address(createOffer.getAddress())
                .company(company)
                .junctionClients(new ArrayList<>())
                .aircraftType(aircraftType)
                .flightPicture(FlightPictureEntity.of(null, pictureFileName))
                .build();
        flightJpaRepository.save(newFlight);
    }

    private Integer companyRating(FlightEntity flightEntity) {
        var ratingSum = Integer.valueOf(flightEntity.getCompany().getComments().stream()
                .map(CommentEntity::getRating)
                .mapToInt(d -> d)
                .sum());
        if(ratingSum <= 0) {
            ratingSum = null;
        }
        else {
            ratingSum = ratingSum / flightEntity.getCompany().getComments().size();
        }
        return ratingSum;
    }

    @Override
    public List<OfferPreview> getUserOfferPreviews() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get user offer previews. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var userId = userDetails.getUserEntity().getId();
        var userFlights = flightJpaRepository.findAllClientActiveOffers(userId, LocalDateTime.now().minusWeeks(2));

        return userFlights.stream()
                .map(flightEntity -> OfferPreview.builder()
                        .flightId(flightEntity.getId())
                        .title(flightEntity.getTitle())
                        .pricePerPerson(flightEntity.getPricePerPerson())
                        .aircraftType(flightEntity.getAircraftType().getName())
                        .address(flightEntity.getAddress())
                        .flightDate(flightEntity.getFlightDate())
                        .imageFilename(flightEntity.getFlightPicture().getPictureFileName())
                        .build())
                .toList();
    }

    @Override
    public List<OrganizerOfferPreview> getOrganizerOfferPreviews() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get organizer offer previews. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var companyFlights = flightJpaRepository
                .findAllOrganizerActiveOffers(userDetails.getUserEntity().getId(), LocalDateTime.now().minusWeeks(2));


        return companyFlights.stream()
                .map(flightEntity -> OrganizerOfferPreview.builder()
                        .flightId(flightEntity.getId())
                        .title(flightEntity.getTitle())
                        .pricePerPerson(flightEntity.getPricePerPerson())
                        .clientCount(flightEntity.getJunctionClients().size())
                        .totalSeats(flightEntity.getTotalSeats())
                        .address(flightEntity.getAddress())
                        .flightDate(flightEntity.getFlightDate())
                        .imageFilename(flightEntity.getFlightPicture().getPictureFileName())
                        .build())
                .toList();
    }

    @Override
    public OrganizerOfferDetails getOrganizerOfferDetails(Integer flightId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            throw new NotAuthenticatedException("Can't get organizer offer details. User is not authenticated");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var flightEntity = flightJpaRepository.findById(flightId)
                .orElseThrow(FlightNotFoundException::new);

        if(userDetails.getUserEntity().getId() != flightEntity.getCompany().getOrganizerAccount().getId())
            throw new NotAuthorizedException("User is not organizer of this offer.");

        var clients = flightEntity.getJunctionClients()
                .stream().map(UserFlightEntity::getUser)
                .map(userEntity -> OfferClient.builder()
                        .email(userEntity.getEmail())
                        .username(userEntity.getUsername())
                        .build()).toList();
        var remainingSeats = flightEntity.getTotalSeats() - flightEntity.getJunctionClients().size();

        return OrganizerOfferDetails.builder()
                .flightId(flightEntity.getId())
                .title(flightEntity.getTitle())
                .description(flightEntity.getDescription())
                .pricePerPerson(flightEntity.getPricePerPerson())
                .remainingSeats(remainingSeats)
                .aircraftType(flightEntity.getAircraftType().getName())
                .imageFilename(flightEntity.getFlightPicture().getPictureFileName())
                .eventOrganizerId(userDetails.getUserEntity().getId())
                .eventOrganizerName(flightEntity.getCompany().getName())
                .eventOrganizerRating(companyRating(flightEntity))
                .address(flightEntity.getAddress())
                .flightDate(flightEntity.getFlightDate())
                .clients(clients)
                .build();
    }
}
