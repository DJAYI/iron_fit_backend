package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "trainment_plans")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private TrainerEntity trainer;

    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private TrainmentPlanObjective objective;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private TrainmentPlanState state;
}
