package com.flynow.infrastructure.repository.jpa;

import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.infrastructure.entities.FlightEntity;
import com.flynow.infrastructure.repository.models.offer.OfferDetailsDbProjection;
import com.flynow.infrastructure.repository.models.offer.OfferPreviewDbProjection;
import com.flynow.infrastructure.repository.models.offer.OrganizerOfferPreviewDbProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightJpaRepository extends JpaRepository<FlightEntity, Integer> {

    @Query("select new com.flynow.infrastructure.repository.models.offer.OfferDetailsDbProjection(" +
            "f.id," +
            "f.title," +
            "f.description," +
            "f.pricePerPerson," +
            "f.totalSeats," +
            "(f.totalSeats - count(uf))," +
            "at.name," +
            "fp.pictureFileName," +
            "c.id," +
            "c.name," +
            "f.address," +
            "f.voivodeship," +
            "f.flightDate)" +
            "from FlightEntity f " +
            "join f.company c " +
            "join f.aircraftType at " +
            "left join FlightPictureEntity fp ON fp.flight = f " +
            "left join UserFlightEntity uf ON uf.flight = f " +
            "WHERE f.id = :flightId " +
            "group by f.id, f.title, f.description, f.pricePerPerson, f.totalSeats, " +
                    "at.name, fp.pictureFileName, c.id, c.name, " +
                    "f.address, f.voivodeship, f.flightDate")
    Optional<OfferDetailsDbProjection> findOfferDetailsProjById(Integer flightId);

    @Query("select new com.flynow.infrastructure.repository.models.offer.OfferPreviewDbProjection(" +
                "f.id," +
                "f.title," +
                "f.pricePerPerson," +
                "at.name," +
                "f.address," +
                "f.voivodeship," +
                "f.flightDate," +
                "fp.pictureFileName) " +
            "from FlightEntity f " +
            "left join f.company " +
            "left join f.aircraftType at " +
            "left join UserFlightEntity uf ON uf.flight = f " +
            "left join FlightPictureEntity fp ON fp.flight = f " +
            "WHERE f.flightDate > CURRENT_DATE " +
            "group by f.id, f.title, f.pricePerPerson, at.name, " +
                    "f.address, f.voivodeship, f.flightDate, fp.pictureFileName " +
            "having count(distinct uf.id) < f.totalSeats " +
            "order by f.flightDate asc")
    List<OfferPreviewDbProjection> findAllActiveWithSeatsLeft();

    @Query("select " +
            "f.id as flightId," +
            "f.title as title," +
            "f.pricePerPerson as pricePerPerson," +
            "at.name as aircraftType," +
            "f.address as address," +
            "f.voivodeship as voivodeship," +
            "f.flightDate as flightDate," +
            "fp.pictureFileName as imageFilename " +
            "from FlightEntity f " +
            "left join f.company " +
            "left join f.aircraftType at " +
            "left join FlightPictureEntity fp ON fp.flight = f " +
            "where f.flightDate >= :afterDate and " +
            "f.id in (SELECT uf.flight.id from UserFlightEntity uf" +
            "           WHERE uf.user.id = :clientId) " +
            "order by f.flightDate asc")
    List<OfferPreviewDbProjection> findAllClientActiveOffers(Integer clientId, LocalDateTime afterDate);

    @Query("select new com.flynow.infrastructure.repository.models.offer.OrganizerOfferPreviewDbProjection( " +
            "f.id," +
            "f.title," +
            "f.pricePerPerson," +
            "count(uf)," +
            "f.totalSeats," +
            "f.address," +
            "f.voivodeship," +
            "f.flightDate," +
            "fp.pictureFileName)" +
            " from FlightEntity f " +
            "left join f.company " +
            "left join UserFlightEntity uf ON uf.flight.id = f.id " +
            "join f.aircraftType at " +
            "left join FlightPictureEntity fp ON fp.flight = f " +
            "WHERE f.flightDate >= :afterDate and " +
            "f.company.organizerAccount.id = :organizerId " +
            "group by f.id, f.title, f.pricePerPerson, f.totalSeats, " +
                    "f.address, f.voivodeship, f.flightDate, fp.pictureFileName " +
            "order by f.flightDate asc")
    List<OrganizerOfferPreviewDbProjection> findAllOrganizerActiveOffers(Integer organizerId, LocalDateTime afterDate);

    @Query("select new com.flynow.infrastructure.repository.models.offer.OfferPreviewDbProjection(" +
            "f.id," +
            "f.title," +
            "f.pricePerPerson," +
            "at.name," +
            "f.address," +
            "f.voivodeship," +
            "f.flightDate," +
            "fp.pictureFileName) " +
            "from FlightEntity f " +
            "left join f.company " +
            "left join UserFlightEntity uf ON uf.flight = f " +
            "left join f.aircraftType at " +
            "left join FlightPictureEntity fp ON fp.flight = f " +
            "WHERE f.flightDate > CURRENT_DATE AND " +
            "f.voivodeship IN :voivodeships " +
            "group by f.id, f.title, f.pricePerPerson, at.name, " +
                    "f.address, f.voivodeship, f.flightDate, fp.pictureFileName " +
            "having count(distinct uf.id) < f.totalSeats " +
            "order by f.flightDate asc")
    List<OfferPreviewDbProjection> findAllByVoivodeshipIn(List<VoivodeshipEnum> voivodeships);

}
