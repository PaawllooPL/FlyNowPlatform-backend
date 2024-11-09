package com.flynow.api.controllers;


import com.flynow.api.dto.user.RegisterUserDTO;
import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.service.models.AuthenticationRequest;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.services.AuthService;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody final RegisterUserDTO registerUserDTO) {
        try {
            logger.debug("Starting /register endpoint");
            var user = User.of(registerUserDTO.getUsername(), registerUserDTO.getEmail(),
                    passwordEncoder.encode(registerUserDTO.getPassword()), new Date(), List.of(RoleEnum.user));
            AuthenticationResponse authenticationResponse = authService.register(user);
            return ResponseEntity.ok(authenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestParam("token") String refreshToken) {
        try {
            AuthenticationResponse res = authService.refreshToken(refreshToken);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        try {
            Boolean res = authService.validateToken(token);
            logger.debug("Validate token result: {}", res);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            if(e instanceof SignatureException) {
                logger.debug("Validate token result: false");
                return ResponseEntity.ok(false);
            }
            logger.error("/validateToken Error validating token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse res = authService.authenticate(authenticationRequest);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            logger.error("Error authenticating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

}
