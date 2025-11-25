package com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nutrition_plans")
public class NutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainment_plan_id", nullable = false)
    private TrainmentPlan trainmentPlan;

    @Column(nullable = false)
    private BigDecimal calories;

    @Column(nullable = false)
    private BigDecimal proteinGrams;

    @Column(nullable = false)
    private BigDecimal carbsGrams;

    @Column(nullable = false)
    private BigDecimal fatGrams;

    @Column(columnDefinition = "TEXT")
    private String description;
}
