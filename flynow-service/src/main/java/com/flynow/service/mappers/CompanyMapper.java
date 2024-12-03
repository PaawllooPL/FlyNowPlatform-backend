package com.flynow.service.mappers;

import com.flynow.domain.models.company.Company;
import com.flynow.domain.models.company.CreateCompany;
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
    public static CompanyEntity toNewEntityWithExistingUser(CreateCompany createCompany, UserEntity user) {
        return CompanyEntity.builder()
                .organizerAccount(user)
                .name(createCompany.getName())
                .tin(createCompany.getTIN())
                .address(createCompany.getAddress())
                .build();
    }
}
