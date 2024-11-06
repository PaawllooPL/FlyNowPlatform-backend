package com.flynow.service.services;

import com.flynow.domain.models.Role;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.mappers.UserMapper;
import com.flynow.service.models.AuthenticationRequest;
import com.flynow.service.models.AuthenticationResponse;
import com.flynow.service.models.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public AuthenticationResponse register(User user) {
        var entityRoles = roleJpaRepository.findAllByNameIn(user.getRoles());
        UserEntity userEntity = userMapper.toEntityWithExistingRoles(user, entityRoles);

        userJpaRepository.save(userEntity);

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
        return jwtService.validateToken(token);
    }
}
