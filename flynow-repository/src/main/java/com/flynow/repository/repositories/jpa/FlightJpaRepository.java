package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightJpaRepository extends JpaRepository<FlightEntity, Integer> {
//    @Query("select f.id, f.title, f.pricePerPerson, at.name, fp.pictureFileName from FlightEntity f " +
//            "left join f.company " +
//            "left join f.junctionClients jc " +
//            "left join f.aircraftType at " +
//            "left join f.flightPicture fp " +
//            "where f.flightDate > CURRENT_DATE " +
//            "group by f.id, fp.pictureFileName, at.name " +
//                    "having count(jc) < f.totalSeats " +
    @Query("select f from FlightEntity f " +
            "left join f.company " +
            "left join f.junctionClients jc " +
            "left join f.aircraftType at " +
            "left join f.flightPicture fp " +
            "where f.flightDate > CURRENT_DATE " +
            "group by f.id " +
                    "having count(jc) < f.totalSeats " +
            "order by f.flightDate asc")
    List<FlightEntity> findAllActiveWithSeatsLeft();
}
