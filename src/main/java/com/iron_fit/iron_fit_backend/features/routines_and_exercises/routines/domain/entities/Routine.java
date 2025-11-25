package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routines")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainment_plan_id", nullable = false)
    private TrainmentPlan trainmentPlan;
}
