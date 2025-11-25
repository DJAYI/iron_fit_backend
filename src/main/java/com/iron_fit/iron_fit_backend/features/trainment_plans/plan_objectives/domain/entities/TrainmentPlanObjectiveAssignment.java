package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.entities;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainment_plan_objective_assignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainmentPlanObjectiveAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainment_plan_id", nullable = false)
    private TrainmentPlan trainmentPlan;

    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private TrainmentPlanObjective objective;
}
