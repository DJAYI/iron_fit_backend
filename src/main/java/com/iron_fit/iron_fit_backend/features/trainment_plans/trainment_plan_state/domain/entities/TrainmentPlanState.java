package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainment_plan_states")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainmentPlanState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
