package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainmentPlanRepository extends JpaRepository<TrainmentPlan, Long> {
    List<TrainmentPlan> findByClientId(Long clientId);

    List<TrainmentPlan> findByTrainerId(Long trainerId);
}
