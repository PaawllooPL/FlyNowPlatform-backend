package com.flynow.service.repository.query;

import com.flynow.domain.models.Company;

import java.util.Optional;

public interface CompanyQueryRepository {
    boolean existsByOrganizerId (Integer organizerId);
    boolean existsByIdAndOrganizerAccountId(Integer id, Integer organizerId);
    Optional<Company> findByOrganizerId(Integer organizerId);
}
