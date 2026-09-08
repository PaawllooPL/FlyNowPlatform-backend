package com.flynow.infrastructure.repository.query;

import com.flynow.domain.models.Company;
import com.flynow.infrastructure.repository.jpa.CompanyJpaRepository;
import com.flynow.service.repository.query.CompanyQueryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {

    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public boolean existsByOrganizerId(Integer organizerId) {
        return companyJpaRepository.existsByOrganizerAccountId(organizerId);
    }

    @Override
    public boolean existsByIdAndOrganizerAccountId(Integer id, Integer organizerId) {
        return companyJpaRepository.existsByIdAndOrganizerAccountId(id, organizerId);
    }

    @Override
    public Optional<Company> findByOrganizerId(Integer organizerId) {
        return companyJpaRepository.findByOrganizerAccountId(organizerId)
                .map(entity -> Company.of(
                        entity.getId(),
                        entity.getOrganizerAccount().getId(),
                        entity.getName(),
                        entity.getTin(),
                        entity.getAddress()
                ));
    }
}
