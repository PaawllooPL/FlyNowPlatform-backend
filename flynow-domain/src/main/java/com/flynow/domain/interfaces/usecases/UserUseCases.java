package com.flynow.domain.interfaces.usecases;


import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;

public interface UserUseCases {
    void userExistsById(Integer id);
    void createUser(User user);
    void addRole(Integer userId, RoleEnum role);
}
