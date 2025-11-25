package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainmentPlanObjectiveRepository extends JpaRepository<TrainmentPlanObjective, Long> {
}
