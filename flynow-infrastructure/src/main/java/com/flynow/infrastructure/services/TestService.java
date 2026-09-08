package com.flynow.infrastructure.services;

import com.flynow.domain.models.AircraftType;
import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.entities.RoleEntity;
import com.flynow.infrastructure.entities.UserEntity;
import com.flynow.infrastructure.mappers.AircraftTypeMapper;
import com.flynow.infrastructure.mappers.CompanyMapper;
import com.flynow.infrastructure.mappers.RoleMapper;
import com.flynow.infrastructure.mappers.UserMapper;
import com.flynow.infrastructure.repository.jpa.AircraftTypeJpaRepository;
import com.flynow.infrastructure.repository.jpa.CompanyJpaRepository;
import com.flynow.infrastructure.repository.jpa.RoleJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.exceptions.user.UserNotFoundException;
import com.flynow.service.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TestService {

    private final CompanyJpaRepository companyJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AircraftTypeJpaRepository aircraftTypeJpaRepository;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(TestService.class);
    private final ImageService imageService;

    @Transactional
    public Company CreateCompany(Company company, Integer organizerId) {
        UserEntity organizerEntity = userJpaRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RoleEntity organizerEntityRole = roleJpaRepository.findByName(RoleEnum.organizer).get();
        organizerEntity.getAccountRoles().add(organizerEntityRole);

        CompanyEntity newCompanyEntity = CompanyEntity.builder()
                .name(company.getName())
                .tin(company.getTIN())
                .address(company.getAddress())
                .organizerAccount(organizerEntity)
                .build();

        CompanyEntity savedEntity = companyJpaRepository.save(newCompanyEntity);

        return CompanyMapper.toDomain(savedEntity);
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

    @Transactional
    public void SaveImage(MultipartFile file) throws IOException {
        String fileName = imageService.saveImageToStorage(file.getOriginalFilename(), file.getBytes())
                .orElseThrow(RuntimeException::new);
        logger.debug("Image saved to storage: {}", fileName);
    }


}
