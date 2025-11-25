package com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditorRepository extends JpaRepository<AuditorEntity, Long> {
}
