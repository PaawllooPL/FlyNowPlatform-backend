package com.flynow.repository.repositories.jpa;

import com.flynow.domain.models.VoivodeshipEnum;
import com.flynow.repository.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightJpaRepository extends JpaRepository<FlightEntity, Integer> {

    @Query("select f from FlightEntity f " + // oferta
            "left join f.company " +
            "left join f.junctionClients jc " + //oferta JOIN lista klientów
            "left join f.aircraftType at " +
            "left join f.flightPicture fp " +
            "where f.flightDate > CURRENT_DATE " + // przed datą wydarzenia
            "group by f.id " +
                    "having count(jc) < f.totalSeats " + // tylko wolne miejsca
            "order by f.flightDate asc")
    List<FlightEntity> findAllActiveWithSeatsLeft();

    @Query("select f from FlightEntity f " +
            "left join f.company " +
            "left join f.junctionClients jc " +
            "left join f.aircraftType at " +
            "left join f.flightPicture fp " +
            "where f.flightDate >= :afterDate and " +
            "jc.user.id = :clientId " +
            "order by f.flightDate asc")
    List<FlightEntity> findAllClientActiveOffers(Integer clientId, LocalDateTime afterDate);

    @Query("select f from FlightEntity f " +
            "left join f.company " +
            "left join f.junctionClients jc " +
            "left join f.aircraftType at " +
            "left join f.flightPicture fp " +
            "where f.flightDate >= :afterDate and " +
            "f.company.organizerAccount.id = :organizerId " +
            "order by f.flightDate asc")
    List<FlightEntity> findAllOrganizerActiveOffers(Integer organizerId, LocalDateTime afterDate);
    @Query("select f from FlightEntity f " +
            "left join f.company " +
            "left join f.junctionClients jc " +
            "left join f.aircraftType at " +
            "left join f.flightPicture fp " +
            "where f.flightDate > CURRENT_DATE " +
            "and f.voivodeship in :voivodeships " +
            "group by f.id " +
            "having count(jc) < f.totalSeats " +
            "order by f.flightDate asc")
    List<FlightEntity> findAllByVoivodeshipIn(List<VoivodeshipEnum> voivodeships);
}
