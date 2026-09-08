package com.flynow.service.repository.query;

import com.flynow.domain.models.User;

import java.util.Optional;

public interface UserQueryRepository {
    boolean existsWithEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
}
