package com.flynow.service.mappers;

import com.flynow.domain.models.Company;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.entities.UserEntity;

public class CompanyMapper {


    public static Company toDomain(CompanyEntity entity) {
        return Company.builder()
                .name(entity.getName())
                .tin(entity.getTin())
                .address(entity.getAddress())
                .comments(entity.getComments().stream().map(CommentMapper::toDomain).toList())
                .build();
    }
    public static CompanyEntity toEntityWithExistingUser(Company company, UserEntity user) {
        return CompanyEntity.builder()
                .organizerAccount(user)
                .name(company.getName())
                .tin(company.getTin())
                .address(company.getAddress())
                .build();
    }
}
