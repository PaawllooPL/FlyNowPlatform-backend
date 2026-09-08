package com.flynow.service.mappers;

// COMMENTED OUT — moved to flynow-infrastructure (depends on infrastructure entities, was placed in service by mistake).
// Kept here as a placeholder so the package and historical code are not lost.
/*
import com.flynow.domain.models.Company;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.entities.UserEntity;

public class CompanyMapper {


    public static Company toDomain(CompanyEntity entity) {
        return Company.builder()
                .name(entity.getName())
                .TIN(entity.getTin())
                .address(entity.getAddress())
                .build();
    }
    public static CompanyEntity toNewEntityWithExistingUser(Company newCompany, UserEntity user) {
        return CompanyEntity.builder()
                .organizerAccount(user)
                .name(newCompany.getName())
                .tin(newCompany.getTIN())
                .address(newCompany.getAddress())
                .build();
    }
}
*/
