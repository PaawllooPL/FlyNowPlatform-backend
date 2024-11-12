package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Company;

public interface CompanyUseCases {
    void createCompany (Company company);
    Comment addComment (Integer companyId, Integer userId, Comment comment);
}
