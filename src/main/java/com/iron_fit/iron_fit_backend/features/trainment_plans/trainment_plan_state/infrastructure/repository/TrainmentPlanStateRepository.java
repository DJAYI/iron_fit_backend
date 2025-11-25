package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainmentPlanStateRepository extends JpaRepository<TrainmentPlanState, Long> {
}
