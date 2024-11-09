package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.Company;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyUseCases {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public Company createCompany(Company company) {
        return null;
    }
}
