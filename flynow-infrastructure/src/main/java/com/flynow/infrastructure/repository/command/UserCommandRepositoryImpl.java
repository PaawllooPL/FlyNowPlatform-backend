package com.flynow.infrastructure.repository.command;

import com.flynow.domain.models.User;
import com.flynow.infrastructure.entities.RoleEntity;
import com.flynow.infrastructure.entities.UserEntity;
import com.flynow.infrastructure.repository.jpa.RoleJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.repository.command.UserCommandRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    @Transactional
    public void save(User user) {
        List<RoleEntity> roles = roleJpaRepository.findAllByNameIn(user.getRoles());
        UserEntity userEntity =
                UserEntity.of(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPasswordHash(),
                        roles);
        userJpaRepository.save(userEntity);
    }

}

