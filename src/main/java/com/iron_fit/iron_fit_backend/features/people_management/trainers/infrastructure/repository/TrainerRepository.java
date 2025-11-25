package com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    Optional<TrainerEntity> findByUser_Id(UUID userId);
}
