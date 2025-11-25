package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainment_plan_objective")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainmentPlanObjective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
