package com.flynow.service.services;

import com.flynow.domain.models.AircraftType;
import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.repositories.jpa.*;
import com.flynow.service.mappers.AircraftTypeMapper;
import com.flynow.service.mappers.CompanyMapper;
import com.flynow.service.mappers.RoleMapper;
import com.flynow.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TestService {

    private final CommentJpaRepository commentJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;
    private final CompanyMapper companyMapper;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Transactional
    public Company CreateCompany(Company company) {
        CompanyEntity companyEntity = companyJpaRepository.findByName(company.getName())
                .orElseThrow(() -> new RuntimeException(String.format("Company by name %s already exists", company.getName())));

        CompanyEntity newCompanyEntity = CompanyEntity.builder()
                .name("testowa firma")
                .tin("19999999999999")
                .address("Katowice Ogrodowa 15")
                .build();

        CompanyEntity savedEntity = companyJpaRepository.save(newCompanyEntity);

        return companyMapper.toDomain(savedEntity);
    }
    @Transactional
    public void CreateRoles(List<RoleEnum> roles) {

        List<RoleEntity> entityRoles = roles.stream().map(RoleMapper::toEntity).toList();
        for (RoleEntity role : entityRoles) {
            if (roleJpaRepository.findByName(role.getName()).isEmpty()) {
                roleJpaRepository.save(role);
                logger.debug("Saved Role: {}", role.getName());
            }
        }
    }
    @Transactional
    public void CreateUsers(List<User> users) {
        for (User user : users) {
            var entityRoles =
                    !user.getRoles().isEmpty() ? roleJpaRepository.findAllByNameIn(user.getRoles()) : new ArrayList<RoleEntity>();
            var entityUser = userMapper.toEntityWithExistingRoles(user, entityRoles);
            userJpaRepository.save(entityUser);
        }
    }
    @Transactional
    public void CreateAircraftTypes(List<AircraftType> aircraftTypes) {
        aircraftTypeJpaRepository.saveAll(
                aircraftTypes.stream().map(AircraftTypeMapper::toEntity).toList());
    }
}
