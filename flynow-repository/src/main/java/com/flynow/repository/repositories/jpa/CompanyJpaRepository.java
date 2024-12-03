package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.CommentEntity;
import com.flynow.repository.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Integer> {
    Optional<CompanyEntity> findByName(String name);
    Optional<CompanyEntity> findByOrganizerAccountId (Integer organizerId);
}
