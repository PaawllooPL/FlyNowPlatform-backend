package com.flynow.api.controllers;


import com.flynow.api.dto.user.RegisterUserDTO;
import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.service.models.AuthenticationRequest;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody final RegisterUserDTO registerUserDTO) {
        try {
            var user = User.of(registerUserDTO.getUsername(), registerUserDTO.getEmail(),
                    passwordEncoder.encode(registerUserDTO.getPassword()), new Date(), List.of(RoleEnum.user));
            AuthenticationResponse authenticationResponse = authService.register(user);
            return ResponseEntity.ok(authenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
