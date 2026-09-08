package com.flynow.api.controllers;


import com.flynow.api.dto.user.LoginUserDTO;
import com.flynow.api.dto.user.RegisterUserDTO;
import com.flynow.service.commands.RegisterUserCommand;
import com.flynow.service.models.command.AuthenticationCommand;
import com.flynow.service.models.result.AuthenticationTokens;
import com.flynow.service.services.AuthService;
import com.flynow.service.usecases.UserUseCases;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH+AUTHENTICATION)
public class AuthenticationController {

    private final AuthService authService;
    private final UserUseCases userUseCases;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(value = AUTHENTICATION_REGISTER/*, headers = MediaType.APPLICATION_JSON_VALUE*/)
    public ResponseEntity<AuthenticationTokens> register(@RequestBody final RegisterUserDTO registerUserDTO) {
            logger.debug("Starting /register endpoint");
            var authenticationResponse = userUseCases.register(new RegisterUserCommand(registerUserDTO.getUsername(), registerUserDTO.getEmail(), registerUserDTO.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
        }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationTokens> refresh(@RequestParam("token") String refreshToken) {
        try {
            AuthenticationTokens res = authService.refreshToken(refreshToken);
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
    @PostMapping(AUTHENTICATION_LOGIN)
    public ResponseEntity<AuthenticationTokens> authenticate(@RequestBody LoginUserDTO request) {
        try {
            logger.debug("Starting /login endpoint. email: {} password: {}", request.getEmail(), request.getPassword());
            var authCommand = new AuthenticationCommand(request.getEmail(), request.getPassword());
            AuthenticationTokens res = authService.authenticate(authCommand);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                logger.debug("{}{} | Invalid username or password", AUTHENTICATION, AUTHENTICATION_LOGIN);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            logger.error("Error authenticating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
