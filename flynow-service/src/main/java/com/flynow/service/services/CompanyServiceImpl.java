package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Company;
import com.flynow.domain.models.RoleEnum;
import com.flynow.repository.entities.CommentEntity;
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
import com.flynow.service.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyUseCases {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Transactional
    @Override
    public void createCompany(Company company) {
        //walidacja
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserEntity organizerEntity = userJpaRepository.findById(userDetails.getUserEntity().getId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("user with id %d not found", userDetails.getUserEntity().getId())));
        RoleEntity organizerEntityRole = roleJpaRepository.findByName(RoleEnum.organizer).get();

        organizerEntity.getAccountRoles().add(organizerEntityRole);
        CompanyEntity companyEntity = CompanyMapper.toEntityWithExistingUser(company, organizerEntity);
        companyJpaRepository.save(companyEntity);
    }


    @Transactional
    public Comment addComment(Integer companyId, Integer userId, Comment comment) {
        logger.error("Comment content: {}", comment.getContent());
        logger.error("Comment rating: {}", comment.getRating());
        CompanyEntity companyEntity = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id: %d not found", companyId)));

        UserEntity userEntity = userJpaRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(String.format("user with id: %d not found", userId)));
        CommentEntity commentEntity = CommentMapper.toEntityWithExistingUser(comment, userEntity);
        logger.error("Comment entity content: {}", commentEntity.getContent());
        logger.error("Comment entity rating: {}", commentEntity.getRating());
        logger.error("Comment entity comment creator email: {}", commentEntity.getCommentCreator().getEmail());
        companyEntity.getComments().add(commentEntity);

        companyJpaRepository.save(companyEntity);
        return comment;
    }
}
