package com.flynow.service.repository.command;

import com.flynow.domain.models.User;


public interface UserCommandRepository {
    void save(User user);
}
