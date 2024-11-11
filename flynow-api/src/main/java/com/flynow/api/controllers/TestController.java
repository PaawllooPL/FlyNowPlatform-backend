package com.flynow.api.controllers;

import com.flynow.api.ApiPathSegments;
import com.flynow.api.dto.company.CreateCompanyDTO;
import com.flynow.api.dto.user.RegisterUserDTO;
import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.AircraftType;
import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.AircraftTypeJpaRepository;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.InsufficientPermissionsException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.mappers.AircraftTypeMapper;
import com.flynow.service.mappers.RoleMapper;
import com.flynow.service.mappers.UserMapper;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.services.TestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
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

        companyUseCases.createCompany(company, "pawel@gmail.com");
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
        return ResponseEntity.ok().body("Created aircraft types");
    }
    @GetMapping("/auth-test")
    public ResponseEntity<String> authTestGet() {

        return ResponseEntity.ok().body("Auth test worked");
    }
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
}
