package com.flynow.service.usecases;


import com.flynow.service.commands.RegisterUserCommand;
import com.flynow.service.models.result.AuthenticationTokens;

public interface UserUseCases {
    AuthenticationTokens register (RegisterUserCommand registerUser) throws RuntimeException;
}
