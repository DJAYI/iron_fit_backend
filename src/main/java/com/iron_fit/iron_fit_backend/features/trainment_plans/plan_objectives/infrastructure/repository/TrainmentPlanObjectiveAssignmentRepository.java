package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.entities.TrainmentPlanObjectiveAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainmentPlanObjectiveAssignmentRepository
        extends JpaRepository<TrainmentPlanObjectiveAssignment, Long> {
    List<TrainmentPlanObjectiveAssignment> findByTrainmentPlanId(Long trainmentPlanId);

    List<TrainmentPlanObjectiveAssignment> findByObjectiveId(Long objectiveId);

    Optional<TrainmentPlanObjectiveAssignment> findByTrainmentPlanIdAndObjectiveId(Long trainmentPlanId,
            Long objectiveId);

    boolean existsByTrainmentPlanIdAndObjectiveId(Long trainmentPlanId, Long objectiveId);
}
