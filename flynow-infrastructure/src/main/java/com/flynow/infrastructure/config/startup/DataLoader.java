package com.flynow.infrastructure.config.startup;

import com.flynow.domain.models.*;
import com.flynow.infrastructure.services.TestService;
import com.flynow.service.repository.command.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserCommandRepository userCommandRepository;
    private final CompanyCommandRepository companyCommandRepository;
    private final FlightCommandRepository flightCommandRepository;
    private final FlightPictureCommandRepository flightPictureCommandRepository;
    private final UserFlightCommandRepository userFlightCommandRepository;
    private final CommentCommandRepository commentCommandRepository;
    private final TestService testService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    // Aircraft types are seeded by TestService.CreateAircraftTypes in this order:
    //   awionetka=1, szybowiec=2, balon=3, helikopter=4.
    private static final Integer AIRCRAFT_AWIONETKA = 1;
    private static final Integer AIRCRAFT_HELIKOPTER = 4;
    private static final Integer AIRCRAFT_BALON = 3;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // DISABLED — the project is mid-refactor and several seed-time types
        // (e.g. testService.CreateUsers, CreateCompany, SaveImage, plus mappers
        // in flynow-infrastructure) are not in their final shape. Leaving the
        // seeder active at app start caused compile errors and runtime
        // failures. Body kept below as a comment so re-enabling is a one-shot
        // uncomment once the supporting files are finalised.
        try {
            testService.CreateRoles(List.of(RoleEnum.admin, RoleEnum.user, RoleEnum.organizer));
            testService.CreateAircraftTypes(List.of(
                    AircraftType.of(null, "awionetka"),
                    AircraftType.of(null, "szybowiec"),
                    AircraftType.of(null, "balon"),
                    AircraftType.of(null, "helikopter")));

            // First user is seeded with the organizer role so they can own a company.
            var pawel = User.of(null, "pawel", "pawel@gmail.com", passwordEncoder.encode("Pawel123!"),
                    List.of(RoleEnum.user, RoleEnum.organizer));
            var users = List.of(
                    pawel,
                    User.of(null, "kuba", "kuba@gmail.com", passwordEncoder.encode("Kuba123!"), List.of(RoleEnum.user)),
                    User.of(null, "kacper", "kacper@gmail.com", passwordEncoder.encode("Kacper123!"), List.of(RoleEnum.user)),
                    User.of(null, "marek", "marek@gmail.com", passwordEncoder.encode("Marek123!"), List.of(RoleEnum.user)),
                    User.of(null, "adam", "adam@gmail.com", passwordEncoder.encode("Adam123!"), List.of(RoleEnum.user)));
            for (var user : users) {
                userCommandRepository.save(user);
            }

            // Company is owned by pawel (the first user saved above).
            Company company = Company.of(null, 1, "Best Flight", "3853917390", "Warszawa, Główna 17");
            companyCommandRepository.save(company);
            Integer companyId = 1;

            // Flight 1
            Integer flight1Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().minusWeeks(1), 45, 399, 5,
                            "Przelot widokowy awionetką nad Bieszczadami – cisza i piękno natury!",
                            "Przeżyj magiczne chwile lecąc nad jednym z najdzikszych i najpiękniejszych regionów Polski – Bieszczadami! " +
                                    "Nasza oferta obejmuje lot widokowy awionetką startującą z lotniska w Ustrzykach Dolnych. " +
                                    "Podczas lotu będziesz mógł podziwiać zielone wzgórza, doliny i jeziora z wyjątkowej perspektywy. " +
                                    "Lot samolotem odbywa się z doświadczonym pilotem, zapewniającym bezpieczeństwo i niezapomniane wrażenia. ",
                            "Lotnisko Ustrzyki Dolne (EPUD) Słoneczna 12", VoivodeshipEnum.podkarpackie, companyId, AIRCRAFT_AWIONETKA));
            flightPictureCommandRepository.save("zdjecie_1.jpg", flight1Id);
            userFlightCommandRepository.save(Passenger.of(null, flight1Id, 2, true));
            userFlightCommandRepository.save(Passenger.of(null, flight1Id, 3, true));
            userFlightCommandRepository.save(Passenger.of(null, flight1Id, 4, false));

            // Flight 2
            Integer flight2Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().minusDays(2), 90, 349, 6,
                            "Przelot widokowy helikopterem nad Mazurami – widoki z nieba!",
                            "Wzbicie się w przestworza nad jednym z najpiękniejszych regionów Polski – Mazurami! " +
                                    "Nasza oferta obejmuje przelot widokowy helikopterem z lotniska w Giżycku. " +
                                    "Zobacz malownicze jeziora, lasy i rozległe pola z perspektywy nieba. " +
                                    "Podczas lotu będziesz mógł podziwiać urokliwą krainę tysiąca jezior oraz okoliczne wioski. " +
                                    "Lot odbywa się w komfortowym śmigłowcu z doświadczonym pilotem, " +
                                    "zapewniającym pełne bezpieczeństwo i niezapomniane wrażenia. " +
                                    "Idealny pomysł na prezent lub wyjątkowy sposób na odkrycie piękna Mazur z innej perspektywy.",
                            "Lotnisko Giżycko (EPLG) Żernickiego 1", VoivodeshipEnum.kujawskoPomorskie, companyId, AIRCRAFT_HELIKOPTER));
            flightPictureCommandRepository.save("zdjecie_2.jpg", flight2Id);
            userFlightCommandRepository.save(Passenger.of(null, flight2Id, 2, false));
            userFlightCommandRepository.save(Passenger.of(null, flight2Id, 3, true));
            userFlightCommandRepository.save(Passenger.of(null, flight2Id, 4, true));
            userFlightCommandRepository.save(Passenger.of(null, flight2Id, 5, true));

            // Flight 3
            Integer flight3Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().plusWeeks(1), 30, 150, 6,
                            "Lot balonem nad Podlasiem – niesamowite widoki z wysokości!",
                            "Zapraszamy na lot balonem nad Podlasiem – regionem pełnym uroku i tradycji! " +
                                    "Oferta obejmuje lot balonem startującym z Białegostoku, gdzie będziesz miał okazję podziwiać " +
                                    "rozległe pola, lasy oraz piękne, małe wioski z lotu ptaka. " +
                                    "Lot odbywa się w komfortowym balonie pilotowanym przez licencjonowanego pilota, " +
                                    "który zadba o Twoje bezpieczeństwo i komfort. " +
                                    "Idealny pomysł na romantyczny prezent lub niezapomnianą przygodę.",
                            "Lotnisko Białystok (EPBI) Błękitna 7", VoivodeshipEnum.podlaskie, companyId, AIRCRAFT_BALON));
            flightPictureCommandRepository.save("zdjecie_3.jpg", flight3Id);
            userFlightCommandRepository.save(Passenger.of(null, flight3Id, 2, false));
            userFlightCommandRepository.save(Passenger.of(null, flight3Id, 5, false));

            // Flight 4
            Integer flight4Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().plusWeeks(2), 50, 200, 7,
                            "Helikopterowy lot widokowy nad Krakowem – poznaj miasto z lotu ptaka!",
                            "Przeżyj niesamowite chwile podczas lotu widokowego helikopterem nad Krakowem! " +
                                    "Nasza oferta obejmuje lot startujący z lotniska w Balicach, podczas którego zobaczysz " +
                                    "Rynek Główny, Zamek Królewski na Wawelu oraz malownicze okolice miasta z lotu ptaka. " +
                                    "Zapewniamy komfortowy helikopter pilotowany przez doświadczonego pilota, który zadba o " +
                                    "bezpieczeństwo i niezapomniane przeżycia. Idealny sposób na wyjątkową przygodę w jednym z najpiękniejszych miast Polski.",
                            "Lotnisko Kraków-Balice (EPKK) Lotnicza 1", VoivodeshipEnum.malopolskie, companyId, AIRCRAFT_HELIKOPTER));
            flightPictureCommandRepository.save("zdjecie_4.jpg", flight4Id);
            userFlightCommandRepository.save(Passenger.of(null, flight4Id, 2, false));
            userFlightCommandRepository.save(Passenger.of(null, flight4Id, 3, false));
            userFlightCommandRepository.save(Passenger.of(null, flight4Id, 4, false));

            // Flight 5
            Integer flight5Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().plusDays(5), 25, 220, 3,
                            "Przelot awionetką z najlepszym intruktorem",
                            "Przeżyj niesamowite chwile podczas lotu widokowego awionetką z doświadczonym instruktorem! " +
                                    "Nasza oferta obejmuje lot, podczas którego nauczysz się podstaw pilotażu i poczujesz prawdziwą adrenalinę za sterami. " +
                                    "To doskonała okazja, by podziwiać piękne krajobrazy z perspektywy lotu ptaka, ciesząc się niezapomnianymi emocjami.",
                            "Lotnisko Warszawa-Babice (EPBC) gen. Sylwestra Kaliskiego 57, 01-476 Warszawa", VoivodeshipEnum.mazowieckie, companyId, AIRCRAFT_AWIONETKA));
            flightPictureCommandRepository.save("zdjecie_5.jpg", flight5Id);
            userFlightCommandRepository.save(Passenger.of(null, flight5Id, 2, false));
            userFlightCommandRepository.save(Passenger.of(null, flight5Id, 3, false));

            // Flight 6
            Integer flight6Id = flightCommandRepository.save(
                    Flight.of(null, LocalDateTime.now().plusDays(6), 25, 220, 3,
                            "Podniebna przygoda w awionetce",
                            "Wzbogać swoje życie o wyjątkowe wspomnienia dzięki podniebnej przygodzie awionetką! " +
                                    "Podziwiaj zapierające dech w piersiach widoki z lotu ptaka, ucząc się jednocześnie podstaw pilotażu. " +
                                    "Lot dostarczy Ci emocji, jakich jeszcze nie doświadczyłeś, i pozwoli poczuć wolność w przestworzach. " +
                                    "Idealny sposób na niezapomniane chwile pełne adrenaliny i pięknych krajobrazów.",
                            "Lotnisko Poznań-Kobylnica (EPPK) Startowa 1, 62-006 Janikowo", VoivodeshipEnum.mazowieckie, companyId, AIRCRAFT_AWIONETKA));
            flightPictureCommandRepository.save("zdjecie_6.jpg", flight6Id);
            userFlightCommandRepository.save(Passenger.of(null, flight6Id, 2, false));
            userFlightCommandRepository.save(Passenger.of(null, flight6Id, 3, false));

            // Comments for the company
            commentCommandRepository.save(Comment.of(null, 2, companyId, "kuba", 4,
                    "Całkiem przyjemny lot, ale siedzenie było trochę twarde."));
            commentCommandRepository.save(Comment.of(null, 3, companyId, "kacper", 5,
                    "Mój pierwszy lot, było super"));
            commentCommandRepository.save(Comment.of(null, 3, companyId, "kacper", 4,
                    "Piękna okolica, ale wolałbym więcej czasu w powietrzu."));
            commentCommandRepository.save(Comment.of(null, 4, companyId, "marek", 5,
                    "Pilot był bardzo profesjonalny, czułem się bezpiecznie."));
            commentCommandRepository.save(Comment.of(null, 5, companyId, "adam", 5,
                    "Widok jezior z góry zapiera dech w piersiach!"));

        } catch (Exception e) {
            logger.error("Seeding database failed", e);
        }
    }
}
