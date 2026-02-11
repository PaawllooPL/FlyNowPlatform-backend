package com.flynow.api.config.startup;

import com.flynow.domain.models.*;
import com.flynow.domain.models.company.Company;
import com.flynow.domain.models.user.User;
import com.flynow.repository.entities.CommentEntity;
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
                    User.of("kuba", "kuba@gmail.com", passwordEncoder.encode("Kuba123!"), Date.from(Instant.now()), List.of(RoleEnum.user)),
                    User.of("kacper", "kacper@gmail.com", passwordEncoder.encode("Kacper123!"), Date.from(Instant.now()), List.of(RoleEnum.user)),
                    User.of("marek", "marek@gmail.com", passwordEncoder.encode("Marek123!"), Date.from(Instant.now()), List.of(RoleEnum.user)),
                    User.of("adam", "adam@gmail.com", passwordEncoder.encode("Adam123!"), Date.from(Instant.now()), List.of(RoleEnum.user)));
                for(var user: users) {
                    authService.register(user);
                }
            }
            if(companyJpaRepository.count() == 0) {
                Company company = Company.of(null, "Best Flight",
                        "3853917390", "Warszawa, Główna 17", List.of());
                testService.CreateCompany(company, 1);
            }
            if(flightJpaRepository.count() == 0) {
                var company = companyJpaRepository.findByName("Best Flight").get();
                var awionetka_type = aircraftTypeJpaRepository.findByName("awionetka").get();
                var szybowiec_type = aircraftTypeJpaRepository.findByName("szybowiec").get();
                var balon_type = aircraftTypeJpaRepository.findByName("balon").get();
                var helikopter_type = aircraftTypeJpaRepository.findByName("helikopter").get();

                var userKuba = userJpaRepository.findByEmail("kuba@gmail.com").get();
                var userKacper = userJpaRepository.findByEmail("kacper@gmail.com").get();
                var userMarek = userJpaRepository.findByEmail("marek@gmail.com").get();
                var userAdam = userJpaRepository.findByEmail("adam@gmail.com").get();

                var flights = List.of(
                    FlightEntity.of(null, LocalDateTime.now().minusWeeks(1), 45, 399, 5,
                            "Przelot widokowy awionetką nad Bieszczadami – cisza i piękno natury!",
                            "Przeżyj magiczne chwile lecąc nad jednym z najdzikszych i najpiękniejszych regionów Polski – Bieszczadami! " +
                                    "Nasza oferta obejmuje lot widokowy awionetką startującą z lotniska w Ustrzykach Dolnych. " +
                                    "Podczas lotu będziesz mógł podziwiać zielone wzgórza, doliny i jeziora z wyjątkowej perspektywy. " +
                                    "Lot samolotem odbywa się z doświadczonym pilotem, zapewniającym bezpieczeństwo i niezapomniane wrażenia. ",
                            "Lotnisko Ustrzyki Dolne (EPUD) Słoneczna 12", VoivodeshipEnum.podkarpackie, company,
                            List.of(
                                    UserFlightEntity.of(null, userKuba, true),
                                    UserFlightEntity.of(null, userKacper, true),
                                    UserFlightEntity.of(null, userMarek, false)
                            ),
                            awionetka_type, FlightPictureEntity.of(null, "zdjecie_1.jpg")
                    ),
                    FlightEntity.of(null, LocalDateTime.now().minusDays(2), 90, 349, 6,
                            "Przelot widokowy helikopterem nad Mazurami – widoki z nieba!"
                            ,"Wzbicie się w przestworza nad jednym z najpiękniejszych regionów Polski – Mazurami! " +
                                    "Nasza oferta obejmuje przelot widokowy helikopterem z lotniska w Giżycku. " +
                                    "Zobacz malownicze jeziora, lasy i rozległe pola z perspektywy nieba. " +
                                    "Podczas lotu będziesz mógł podziwiać urokliwą krainę tysiąca jezior oraz okoliczne wioski. " +
                                    "Lot odbywa się w komfortowym śmigłowcu z doświadczonym pilotem, " +
                                    "zapewniającym pełne bezpieczeństwo i niezapomniane wrażenia. " +
                                    "Idealny pomysł na prezent lub wyjątkowy sposób na odkrycie piękna Mazur z innej perspektywy.",
                            "Lotnisko Giżycko (EPLG) Żernickiego 1", VoivodeshipEnum.kujawskoPomorskie, company,
                        List.of(
                                UserFlightEntity.of(null, userKuba, false),
                                UserFlightEntity.of(null, userKacper, true),
                                UserFlightEntity.of(null, userMarek, true),
                                UserFlightEntity.of(null, userAdam, true)
                        ),
                            helikopter_type, FlightPictureEntity.of(null, "zdjecie_2.jpg")
                    ),
                    FlightEntity.of(null, LocalDateTime.now().plusWeeks(1), 30, 150, 6,
                            "Lot balonem nad Podlasiem – niesamowite widoki z wysokości!",
                            "Zapraszamy na lot balonem nad Podlasiem – regionem pełnym uroku i tradycji! " +
                                    "Oferta obejmuje lot balonem startującym z Białegostoku, gdzie będziesz miał okazję podziwiać " +
                                    "rozległe pola, lasy oraz piękne, małe wioski z lotu ptaka. " +
                                    "Lot odbywa się w komfortowym balonie pilotowanym przez licencjonowanego pilota, " +
                                    "który zadba o Twoje bezpieczeństwo i komfort. " +
                                    "Idealny pomysł na romantyczny prezent lub niezapomnianą przygodę.",
                            "Lotnisko Białystok (EPBI) Błękitna 7", VoivodeshipEnum.podlaskie, company,
                            List.of(
                                    UserFlightEntity.of(null, userKuba, false),
                                    UserFlightEntity.of(null, userAdam, false)
                            ),
                            balon_type, FlightPictureEntity.of(null, "zdjecie_3.jpg")
                    ),
                    FlightEntity.of(null, LocalDateTime.now().plusWeeks(2), 50, 200, 7,
                        "Helikopterowy lot widokowy nad Krakowem – poznaj miasto z lotu ptaka!",
                        "Przeżyj niesamowite chwile podczas lotu widokowego helikopterem nad Krakowem! " +
                                "Nasza oferta obejmuje lot startujący z lotniska w Balicach, podczas którego zobaczysz " +
                                "Rynek Główny, Zamek Królewski na Wawelu oraz malownicze okolice miasta z lotu ptaka. " +
                                "Zapewniamy komfortowy helikopter pilotowany przez doświadczonego pilota, który zadba o " +
                                "bezpieczeństwo i niezapomniane przeżycia. Idealny sposób na wyjątkową przygodę w jednym z najpiękniejszych miast Polski.",
                        "Lotnisko Kraków-Balice (EPKK) Lotnicza 1", VoivodeshipEnum.malopolskie, company,
                        List.of(
                                UserFlightEntity.of(null, userKuba, false),
                                UserFlightEntity.of(null, userKacper, false),
                                UserFlightEntity.of(null, userMarek, false)
                        ),
                        helikopter_type, FlightPictureEntity.of(null, "zdjecie_4.jpg")
                    ),
                    FlightEntity.of(null, LocalDateTime.now().plusDays(5), 25, 220, 3,
                            "Przelot awionetką z najlepszym intruktorem",
                            "Przeżyj niesamowite chwile podczas lotu widokowego awionetką z doświadczonym instruktorem! " +
                                    "Nasza oferta obejmuje lot, podczas którego nauczysz się podstaw pilotażu i poczujesz prawdziwą adrenalinę za sterami. " +
                                    "To doskonała okazja, by podziwiać piękne krajobrazy z perspektywy lotu ptaka, ciesząc się niezapomnianymi emocjami.",
                            "Lotnisko Warszawa-Babice (EPBC) gen. Sylwestra Kaliskiego 57, 01-476 Warszawa", VoivodeshipEnum.mazowieckie, company,
                            List.of(
                                    UserFlightEntity.of(null, userKuba, false),
                                    UserFlightEntity.of(null, userKacper, false)
                            ),
                            helikopter_type, FlightPictureEntity.of(null, "zdjecie_5.jpg")
                    ),
                    FlightEntity.of(null, LocalDateTime.now().plusDays(6), 25, 220, 3,
                            "Podniebna przygoda w awionetce",
                            "Wzbogać swoje życie o wyjątkowe wspomnienia dzięki podniebnej przygodzie awionetką! " +
                                    "Podziwiaj zapierające dech w piersiach widoki z lotu ptaka, ucząc się jednocześnie podstaw pilotażu. " +
                                    "Lot dostarczy Ci emocji, jakich jeszcze nie doświadczyłeś, i pozwoli poczuć wolność w przestworzach. " +
                                    "Idealny sposób na niezapomniane chwile pełne adrenaliny i pięknych krajobrazów.",
                            "Lotnisko Poznań-Kobylnica (EPPK) Startowa 1, 62-006 Janikowo", VoivodeshipEnum.mazowieckie, company,
                            List.of(
                                    UserFlightEntity.of(null, userKuba, false),
                                    UserFlightEntity.of(null, userKacper, false)
                            ),
                            helikopter_type, FlightPictureEntity.of(null, "zdjecie_6.jpg")
                    ));

                flightJpaRepository.saveAll(flights);

                company.getComments().addAll(List.of(
                        CommentEntity.of(null, 4,"Całkiem przyjemny lot, ale siedzenie było trochę twarde.", userKuba),
                        CommentEntity.of(null, 5,"Mój pierwszy lot, było super", userKacper),

                        CommentEntity.of(null, 4, "Piękna okolica, ale wolałbym więcej czasu w powietrzu.", userKacper),
                        CommentEntity.of(null, 5, "Pilot był bardzo profesjonalny, czułem się bezpiecznie.", userMarek),
                        CommentEntity.of(null, 5, "Widok jezior z góry zapiera dech w piersiach!", userAdam)

//                        CommentEntity.of(null, 5, "Lot przebiegł sprawnie i zgodnie z planem. Polecam!", userKacper),
//                        CommentEntity.of(null, 5, "Niesamowite przeżycie, warto spróbować!", userKuba),
//                        CommentEntity.of(null, 5, "Idealny pomysł na prezent, świetna atmosfera!", userKuba),
//                        CommentEntity.of(null, 5, "Doskonała organizacja i niezapomniane widoki!", userKuba)
                ));

                companyJpaRepository.save(company);
            }

        } catch (Exception e) {
            logger.error("Seeding database failed: {}", e.getMessage());
        }
    }
}
