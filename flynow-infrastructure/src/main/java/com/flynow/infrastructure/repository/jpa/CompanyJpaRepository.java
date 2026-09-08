package com.flynow.infrastructure.repository.jpa;

import com.flynow.infrastructure.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Integer> {

    Optional<CompanyEntity> findByName(String name);
    Optional<CompanyEntity> findByOrganizerAccountId (Integer organizerId);
    boolean existsByOrganizerAccountId(Integer organizerAccountId);
    boolean existsByIdAndOrganizerAccountId(Integer id, Integer organizerAccountId);
}
