package com.flynow.api.controllers;

import com.flynow.api.ApiPathSegments;
import com.flynow.api.dto.comment.AddCommentDTO;
import com.flynow.api.dto.comment.CommentDTO;
import com.flynow.api.dto.company.CreateCompanyDTO;
import com.flynow.api.dto.offer.CreateOfferDTO;
import com.flynow.api.dto.user.RegisterUserDTO;
import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.*;
import com.flynow.repository.entities.*;
import com.flynow.repository.repositories.jpa.AircraftTypeJpaRepository;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.InsufficientPermissionsException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.mappers.AircraftTypeMapper;
import com.flynow.service.mappers.CommentMapper;
import com.flynow.service.mappers.RoleMapper;
import com.flynow.service.mappers.UserMapper;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.services.ImageService;
import com.flynow.service.services.TestService;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    private final RoleJpaRepository roleJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;
    private final RoleMapper roleMapper;
    private final TestService testService;
    private final ImageService imageService;
    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyUseCases companyUseCases;

    @PostMapping()
    public ResponseEntity<String> testPost() {
        return ResponseEntity.ok().body("Post Hello");
    }

    @GetMapping(TEST_CREATE_ROLES_URL)
    public ResponseEntity<String> testCreateRoles() {
        try {
            testService.CreateRoles(List.of(RoleEnum.admin, RoleEnum.user, RoleEnum.organizer));
        }
        catch (Exception e) {
            logger.error("Creating roles failed: {}", e.getMessage());
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Created roles");
    }
    @GetMapping(ApiPathSegments.TEST_CREATE_USERS_URL)
    public ResponseEntity<String> testCreateUsers() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var users = List.of(
                User.of("pawel", "pawel@gmail.com", "Pawel123!", Date.from(Instant.now()), List.of(RoleEnum.user, RoleEnum.organizer)),
                User.of("kuba", "kuba@gmail.com", "Kuba123!", Date.from(Instant.now()), List.of(RoleEnum.user)),
                User.of("szymon", "szymon@gmail.com", "Szymon123!", Date.from(Instant.now()), List.of(RoleEnum.user)),
                User.of("kacper", "kacper@gmail.com", "Kacper123!", Date.from(Instant.now()), List.of(RoleEnum.user)));
            users.forEach(user -> {
                var registerUserDto = new RegisterUserDTO(user.getUsername(), user.getEmail(), user.getPasswordHash());
                HttpEntity<RegisterUserDTO> entity = new HttpEntity<>(registerUserDto, headers);
                var rest = new RestTemplate();
                var response = rest.postForEntity(ABSOLUTE_BASE_PATH+AUTHENTICATION+AUTHENTICATION_REGISTER, entity, AuthenticationResponse.class);
                logger.debug("Creating test user. Status code: {}", response.getStatusCode());
            });
            return ResponseEntity.ok().body("Created users");
        }
        catch (Exception e) {
            logger.error("Creating users failed: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping(TEST_CREATE_COMPANY_URL)
    public ResponseEntity<String> testCreateCompany() {

        CreateCompanyDTO createCompanyDTO = CreateCompanyDTO.of("Best Flight",
                "3853917390", "Warszawa, Główna 17");

        Company company = Company.of(null, createCompanyDTO.getName(),
                createCompanyDTO.getTIN(), createCompanyDTO.getAddress(), List.of());

        testService.CreateCompany(company, 1);
        return ResponseEntity.ok().body("Created Company");
    }

    @GetMapping(TEST_CREATE_AIRCRAFT_TYPE_URL)
    public ResponseEntity<String> testCreateAircraftType() {

        var aircraftTypes = List.of(
                AircraftType.of("awionetka"),
                AircraftType.of("szybowiec"),
                AircraftType.of("balon"),
                AircraftType.of("helikopter"));

        testService.CreateAircraftTypes(aircraftTypes);
        return ResponseEntity.ok().body("Created aircraft types.");
    }

    @GetMapping(TEST_CREATE_FLIGHT_URL)
    public ResponseEntity<String> testCreateFlight() {
        var company = companyJpaRepository.findByName("Best Flight").get();
        var awionetka_type = aircraftTypeJpaRepository.findByName("awionetka").get();
        var helikopter_type = aircraftTypeJpaRepository.findByName("helikopter").get();
        var user_commented = userJpaRepository.findByEmail("kacper@gmail.com").get();
        var user_not_commented = userJpaRepository.findByEmail("kuba@gmail.com").get();

        var flights = List.of(
                FlightEntity.of(null, LocalDateTime.now().plusWeeks(2), 120, 399, 4,
                        "opis opis opis lorem ipsum", company, List.of(), awionetka_type,
                        FlightPictureEntity.of(null, "zdjecie_1.jpg")),
                FlightEntity.of(null, LocalDateTime.now().plusWeeks(1), 60, 199, 4,
                        "opis opis opis lorem ipsum", company, List.of(), awionetka_type,
                        FlightPictureEntity.of(null, "zdjecie_2.jpg")),
                FlightEntity.of(null, LocalDateTime.now().minusWeeks(1), 60, 99, 2,
                        "opis opis opis lorem ipsum", company,
                        List.of(UserFlightEntity.of(null, user_commented, true),
                                UserFlightEntity.of(null, user_not_commented, false)),
                        helikopter_type, FlightPictureEntity.of(null, "zdjecie_3.jpg")));
    }


//    @GetMapping(TEST_CREATE_COMMENTS_URL)
//    public ResponseEntity<String> testCreateComments() {
//        userJpaRepository.findByEmail("kacper@gmail.com");
//    }

    @GetMapping(ApiPathSegments.TEST_CREATE_ALL_URL)
    public ResponseEntity<String> testCreateAll() {
        try {
            var restTemplate = new RestTemplate();
            restTemplate.getForEntity(ABSOLUTE_BASE_PATH+TEST_CREATE_ROLES_URL, String.class);
            restTemplate.getForEntity(ABSOLUTE_BASE_PATH+TEST_CREATE_USERS_URL, String.class);
            restTemplate.getForEntity(ABSOLUTE_BASE_PATH+TEST_CREATE_COMPANY_URL, String.class);
            restTemplate.getForEntity(ABSOLUTE_BASE_PATH+TEST_CREATE_AIRCRAFT_TYPE_URL, String.class);
        } catch (Exception e) {
            logger.error("Creating data failed: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Created all data");
    }
    @PostMapping(AUTHENTICATION_TEST)
    public ResponseEntity<CommentDTO> authTestGet(@RequestBody AddCommentDTO addCommentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        logger.debug("User database id: {}.", userDetails.getUserEntity().getId());

        logger.error("Comment rating: {}", addCommentDTO.getRating());
        logger.error("Comment content: {}", addCommentDTO.getContent());

        Comment comment = companyUseCases.addComment(addCommentDTO.getCompanyId(),
                userDetails.getUserEntity().getId(),addCommentDTO.toDomainComment());

        CommentDTO commentDTO = CommentDTO.of(userDetails.getUserEntity().getId(),
                userDetails.getUserEntity().getUsername(), comment.getRating(), comment.getContent());

        return ResponseEntity.ok().body(commentDTO);
    }
    @PostMapping(value = TEST_CREATE_DATA+"/add-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addPhoto(@ModelAttribute CreateOfferDTO createOfferDTO) {
        try {
            imageService.saveImageToStorage(createOfferDTO.getImage());
        } catch (Exception e) {
            logger.error("Error while saving image: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error while saving image.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Added photo");
    }
}
