package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.entities.RoleEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.RoleJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.CompanyNotFoundException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.mappers.CommentMapper;
import com.flynow.service.mappers.CompanyMapper;
import com.flynow.service.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;

import javax.management.relation.RoleNotFoundException;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyUseCases {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public void createCompany(Company company, String organizerEmail) {
        //walidacja
        UserEntity organizerEntity = userJpaRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("user with email %s not found", company.getOrganizer().getEmail())));
        RoleEntity organizerEntityRole = roleJpaRepository.findByName(RoleEnum.organizer).get();

        organizerEntity.getAccountRoles().add(organizerEntityRole);
        CompanyEntity companyEntity = CompanyMapper.toEntityWithExistingUser(company, organizerEntity);
        companyJpaRepository.save(companyEntity);
    }

    @Override
    public Comment addComment(Integer companyId, Integer userId, Comment comment) {
        return null;
    }

    public Comment addComment(Integer companyId, String userEmail, Comment comment) {
        CompanyEntity companyEntity = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id: %d not found", companyId)));

        UserEntity userEntity = userJpaRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UserNotFoundException(String.format("user with email: %s not found", userEmail)));
        companyEntity.getComments().add(CommentMapper.toEntityWithExistingUser(comment, userEntity));

        return comment;
    }
}
