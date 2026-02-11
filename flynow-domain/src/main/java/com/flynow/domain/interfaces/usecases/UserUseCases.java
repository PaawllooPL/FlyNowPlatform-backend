package com.flynow.domain.interfaces.usecases;


import com.flynow.domain.models.user.RegisterUser;
import com.flynow.domain.models.user.User;

public interface UserUseCases {
    User register (RegisterUser registerUser);
}
