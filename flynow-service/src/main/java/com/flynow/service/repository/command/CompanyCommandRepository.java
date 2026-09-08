package com.flynow.service.repository.command;

import com.flynow.domain.models.Company;

public interface CompanyCommandRepository {

    void save(Company company);
}
