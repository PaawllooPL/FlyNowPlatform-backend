package com.flynow.infrastructure.repository.query;

import com.flynow.domain.models.User;
import com.flynow.infrastructure.entities.RoleEntity;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.repository.query.UserQueryRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsWithEmail(String email) {
        var user = userJpaRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var userEntity = userJpaRepository.findByEmail(email);

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            return Optional.of(User.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .passwordHash(user.getPasswordHash())
                    .roles(user.getAccountRoles().stream().map(RoleEntity::getName).toList())
                    .build());
        } else return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        var userEntity = userJpaRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            return Optional.of(User.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .passwordHash(user.getPasswordHash())
                    .roles(user.getAccountRoles().stream().map(RoleEntity::getName).toList())
                    .build());
        } else return Optional.empty();
    }
}
