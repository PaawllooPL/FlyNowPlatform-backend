package com.flynow.service.services;

import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.domain.models.User;
import com.flynow.service.commands.CreateCompanyCommand;
import com.flynow.service.exceptions.company.CompanyAlreadyExistsException;
import com.flynow.service.exceptions.user.NotAuthenticatedException;
import com.flynow.service.exceptions.user.UserNotFoundException;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.repository.command.CompanyCommandRepository;
import com.flynow.service.repository.command.UserCommandRepository;
import com.flynow.service.repository.query.CompanyQueryRepository;
import com.flynow.service.repository.query.UserQueryRepository;
import com.flynow.service.usecases.CompanyUseCases;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyUseCases {

    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final CompanyQueryRepository companyQueryRepository;
    private final CompanyCommandRepository companyCommandRepository;

    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public void createCompany(CreateCompanyCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            logger.debug("[REJECTED] Company create: Authentication is null.");
            throw new NotAuthenticatedException();
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if(companyQueryRepository.existsByOrganizerId(userDetails.getId()))
                throw new CompanyAlreadyExistsException("User is already organizer");

        User user = userQueryRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("user with id %d not found", userDetails.getId())));

        user.getRoles().add(RoleEnum.organizer);

        Company company = Company.of(null, userDetails.getId(), command.name(), command.TIN(), command.address());

        companyCommandRepository.save(company);
        userCommandRepository.save(user);
        logger.debug("Created company {}. Organizer id: {}", company.getName(), user.getId());
    }
}
