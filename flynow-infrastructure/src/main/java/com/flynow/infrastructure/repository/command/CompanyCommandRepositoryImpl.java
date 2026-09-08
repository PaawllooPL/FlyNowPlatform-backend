package com.flynow.infrastructure.repository.command;

import com.flynow.domain.models.Company;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.repository.jpa.CompanyJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.repository.command.CompanyCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyCommandRepositoryImpl implements CompanyCommandRepository {

    private final CompanyJpaRepository companyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(Company company) {
        var userProxy = userJpaRepository.getReferenceById(company.getUserId());
        var companyEntity = CompanyEntity.of(
                company.getId(),
                company.getName(),
                company.getTIN(),
                company.getAddress(),
                userProxy);

        companyJpaRepository.save(companyEntity);
    }
}
