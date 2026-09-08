package com.flynow.service.services;

import com.flynow.domain.models.User;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.models.command.AuthenticationCommand;
import com.flynow.service.models.result.AuthenticationTokens;
import com.flynow.service.repository.query.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;


@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserQueryRepository userQueryRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthenticationTokens generateTokensFor(User user) {

        var userDetails = new UserDetailsImpl(user);
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationTokens.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationTokens authenticate(AuthenticationCommand authenticationCommand) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationCommand.email(), authenticationCommand.password())
        );
        var user = userQueryRepository.findByEmail(authenticationCommand.email()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var userDetails = new UserDetailsImpl(user);
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationTokens.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationTokens refreshToken(String refreshToken) {

        var user = userQueryRepository.findByEmail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var userDetails = new UserDetailsImpl(user);
        var jwtToken = jwtService.generateToken(userDetails);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationTokens.builder()
                .authenticationToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        logger.debug("AuthService calling jwtService.validateToken(token)");
        return jwtService.validateToken(token);
    }
}
