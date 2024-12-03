package com.flynow.api.config.startup;

import com.flynow.domain.models.AircraftType;
import com.flynow.domain.models.company.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.FlightEntity;
import com.flynow.repository.entities.FlightPictureEntity;
import com.flynow.repository.entities.UserFlightEntity;
import com.flynow.repository.repositories.jpa.*;
import com.flynow.service.services.AuthService;
import com.flynow.service.services.TestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleJpaRepository roleJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;
    private final FlightJpaRepository flightJpaRepository;
    private final TestService testService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        try {
            if(roleJpaRepository.count() == 0) {
                testService.CreateRoles(List.of(RoleEnum.admin, RoleEnum.user, RoleEnum.organizer));
            }
            if(aircraftTypeJpaRepository.count() == 0) {
                testService.CreateAircraftTypes(List.of(
                        AircraftType.of("awionetka"),
                        AircraftType.of("szybowiec"),
                        AircraftType.of("balon"),
                        AircraftType.of("helikopter")));
            }
            if(userJpaRepository.count() == 0) {
                var users = List.of(
                    User.of("pawel", "pawel@gmail.com", passwordEncoder.encode("Pawel123!"), Date.from(Instant.now()), List.of(RoleEnum.user)),
                    User.of("kuba", "kuba@gmail.com", passwordEncoder.encode("Kuba123!"), Date.from(Instant.now()), List.of(RoleEnum.user)));
                authService.register(users.get(0));
                authService.register(users.get(1));
            }
            if(companyJpaRepository.count() == 0) {
                Company company = Company.of(null, "Best Flight",
                        "3853917390", "Warszawa, Główna 17", List.of());
                testService.CreateCompany(company, 1);
            }
            if(flightJpaRepository.count() == 0) {
                var company = companyJpaRepository.findByName("Best Flight").get();
                var awionetka_type = aircraftTypeJpaRepository.findByName("awionetka").get();
                var helikopter_type = aircraftTypeJpaRepository.findByName("helikopter").get();
                var user = userJpaRepository.findByEmail("kuba@gmail.com").get();

                var flights = List.of(
                    FlightEntity.of(null, LocalDateTime.now().plusWeeks(1), 120, 399, 4,
                            "Opis zdjecie1","opis opis opis lorem ipsum", "Lotnisko Katowice Długa 68", company, List.of(), awionetka_type,
                        FlightPictureEntity.of(null, "zdjecie_1.jpg")),
                    FlightEntity.of(null, LocalDateTime.now().plusWeeks(2), 60, 199, 4,
                            "Opis zdjecie2","opis opis opis lorem ipsum", "Lotnisko Warszawa Krótka 17", company,
                        List.of(UserFlightEntity.of(null, user, false)), awionetka_type,
                        FlightPictureEntity.of(null, "zdjecie_2.jpg")),
                    FlightEntity.of(null, LocalDateTime.now().minusWeeks(1), 60, 99, 2,
                            "Opis zdjecie3","opis opis opis lorem ipsum", "Lotnisko Dubaj Alsheirk 28", company,
                        List.of(UserFlightEntity.of(null, user, false)), helikopter_type,
                        FlightPictureEntity.of(null, "zdjecie_3.jpg")));

                flightJpaRepository.saveAll(flights);
            }

        } catch (Exception e) {
            logger.error("Seeding database failed: {}", e.getMessage());
        }
    }
}
