package com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerExpertiseRepository extends JpaRepository<TrainerExpertise, Long> {
}
