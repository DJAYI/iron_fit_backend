package com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
    List<NutritionPlan> findByTrainmentPlanId(Long trainmentPlanId);
}
