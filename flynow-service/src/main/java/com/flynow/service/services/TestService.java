package com.flynow.service.services;

import com.flynow.domain.models.Company;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.repositories.jpa.CommentJpaRepository;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.service.mappers.CompanyMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestService {
    private CommentJpaRepository commentJpaRepository;
    private CompanyJpaRepository companyJpaRepository;
    private CompanyMapper companyMapper;

    public Company CreateCompany(Company company) {
        CompanyEntity companyEntity =  companyJpaRepository.findByName(company.getName())
                .orElseThrow(() -> new RuntimeException(String.format("Company by name %s already exists", company.getName())));

        CompanyEntity newCompanyEntity = CompanyEntity.builder()
                .name("testowa firma")
                .tin("19999999999999")
                .address("Katowice Ogrodowa 15")
                .build();

        CompanyEntity savedEntity = companyJpaRepository.save(newCompanyEntity);

        return companyMapper.toCompany(savedEntity);
    }

}
