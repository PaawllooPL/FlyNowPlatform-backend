package com.flynow.service.services;

import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.service.commands.RegisterUserCommand;
import com.flynow.service.exceptions.user.UserAlreadyExistsException;
import com.flynow.service.models.result.AuthenticationTokens;
import com.flynow.service.repository.command.UserCommandRepository;
import com.flynow.service.repository.query.UserQueryRepository;
import com.flynow.service.usecases.UserUseCases;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCases {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    public AuthenticationTokens register(RegisterUserCommand registerUser) throws RuntimeException{

        if (userQueryRepository.existsWithEmail(registerUser.email()))
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", registerUser.email()));
        var user = User.of(null, registerUser.username(), registerUser.email(),
                passwordEncoder.encode(registerUser.password()), List.of(RoleEnum.user));
        userCommandRepository.save(user);
        return authService.generateTokensFor(user);
    }
}
