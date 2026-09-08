package com.flynow.service.usecases;

import com.flynow.service.commands.CreateCompanyCommand;

public interface CompanyUseCases {
    void createCompany (CreateCompanyCommand command);
}
