package com.flynow.service.services;

import com.flynow.domain.models.user.User;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.mappers.UserMapper;
import com.flynow.service.models.AuthenticationRequest;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.models.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public AuthenticationResponse register(User user) {
        logger.debug("Finding roles ...");
        List<RoleEntity> entityRoles =
                !user.getRoles().isEmpty() ? roleJpaRepository.findAllByNameIn(user.getRoles()) : new ArrayList<>();

        logger.debug("Found {} roles", entityRoles.size());
        for(var role : entityRoles) {
            logger.debug("Role: {}", role.getName());
        }

        UserEntity userEntity = userMapper.toEntityWithExistingRoles(user, entityRoles);
        logger.debug("Mapped userEntity: {}, {}, {}, {}, first role: {}", userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPasswordHash(), userEntity.getAccountRoles().get(0).getName());
        userJpaRepository.save(userEntity);
        logger.debug("Saved userEntity");

        var userDetails = new UserDetailsImpl(userEntity);
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user = userJpaRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var userDetails = new UserDetailsImpl(user);
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) {

        var user = userJpaRepository.findByEmail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var userDetails = new UserDetailsImpl(user);
        var jwtToken = jwtService.generateToken(userDetails);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(), userDetails);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        logger.debug("AuthService calling jwtService.validateToken(token)");
        return jwtService.validateToken(token);
    }
}
