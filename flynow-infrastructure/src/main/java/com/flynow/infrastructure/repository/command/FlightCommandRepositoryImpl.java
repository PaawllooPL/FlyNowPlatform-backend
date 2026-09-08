package com.flynow.infrastructure.repository.command;

import com.flynow.domain.models.Flight;
import com.flynow.infrastructure.entities.AircraftTypeEntity;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.entities.FlightEntity;
import com.flynow.infrastructure.repository.jpa.AircraftTypeJpaRepository;
import com.flynow.infrastructure.repository.jpa.CompanyJpaRepository;
import com.flynow.infrastructure.repository.jpa.FlightJpaRepository;
import com.flynow.service.repository.command.FlightCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightCommandRepositoryImpl implements FlightCommandRepository {

    private final FlightJpaRepository flightJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;

    @Override
    public Integer save(Flight flight) {
        CompanyEntity companyProxy = companyJpaRepository.getReferenceById(flight.getCompanyId());
        AircraftTypeEntity aircraftTypeProxy = aircraftTypeJpaRepository.getReferenceById(flight.getAircraftTypeId());

        FlightEntity newFlight = FlightEntity.builder()
                .id(null)
                .flightDate(flight.getFlightDate())
                .duration(flight.getFlightDuration())
                .pricePerPerson(flight.getPricePerPerson())
                .totalSeats(flight.getTotalSeats())
                .title(flight.getTitle())
                .description(flight.getDescription())
                .address(flight.getAddress())
                .voivodeship(flight.getVoivodeship())
                .company(companyProxy)
                .aircraftType(aircraftTypeProxy)
                .build();

        return flightJpaRepository.save(newFlight).getId();
    }
}
