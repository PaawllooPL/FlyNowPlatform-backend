package com.flynow.infrastructure.mappers;

import com.flynow.domain.models.Company;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.entities.UserEntity;

public class CompanyMapper {


    public static Company toDomain(CompanyEntity entity) {
        // Company field order: id, userId, name, TIN, address
        // The JPA entity does not carry userId directly; the User lives in a separate FK.
        return Company.of(entity.getId(), null, entity.getName(), entity.getTin(), entity.getAddress());
    }
    public static CompanyEntity toNewEntityWithExistingUser(Company newCompany, UserEntity user) {
        // CompanyEntity field order: id, name, tin, address, organizerAccount
        return CompanyEntity.of(
                null,
                newCompany.getName(),
                newCompany.getTIN(),
                newCompany.getAddress(),
                user);
    }
}
