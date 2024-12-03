package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.company.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.company.CreateCompany;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.BadRequestException;
import com.flynow.service.exceptions.NotAuthenticatedException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.mappers.CompanyMapper;
import com.flynow.service.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyUseCases {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Transactional
    @Override
    public void createCompany(CreateCompany createCompany) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            logger.debug("Authentication is null");
            throw new NotAuthenticatedException();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        companyJpaRepository.findByOrganizerAccountId(userDetails.getUserEntity().getId())
                .ifPresent((company) -> {throw new BadRequestException("User is already organizer");});

        UserEntity organizerEntity = userJpaRepository.findById(userDetails.getUserEntity().getId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("user with id %d not found", userDetails.getUserEntity().getId())));
        RoleEntity organizerEntityRole = roleJpaRepository.findByName(RoleEnum.organizer).get();

        organizerEntity.getAccountRoles().add(organizerEntityRole);
        CompanyEntity companyEntity = CompanyMapper.toNewEntityWithExistingUser(createCompany, organizerEntity);
        logger.debug("Created company {}. Organizer id: {}", companyEntity.getName(), organizerEntity.getId());
        companyJpaRepository.save(companyEntity);
    }
}
