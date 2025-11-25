package com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services;

import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests.RegisterNutritionPlanDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.responses.NutritionPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities.NutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure.repository.NutritionPlanRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterNutritionPlan {

    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    public HashMap<String, Object> execute(RegisterNutritionPlanDto dto) {
        try {
            // Validate training plan exists
            TrainmentPlan trainmentPlan = trainmentPlanRepository.findById(dto.trainmentPlanId())
                    .orElseThrow(() -> new RuntimeException("Training plan not found"));

            // Create nutrition plan
            NutritionPlan nutritionPlan = NutritionPlan.builder()
                    .trainmentPlan(trainmentPlan)
                    .calories(dto.calories())
                    .proteinGrams(dto.proteinGrams())
                    .carbsGrams(dto.carbsGrams())
                    .fatGrams(dto.fatGrams())
                    .description(dto.description())
                    .build();

            NutritionPlan saved = nutritionPlanRepository.save(nutritionPlan);

            NutritionPlanResponseDto response = new NutritionPlanResponseDto(
                    saved.getId(),
                    saved.getTrainmentPlan().getId(),
                    saved.getTrainmentPlan().getName(),
                    saved.getCalories(),
                    saved.getProteinGrams(),
                    saved.getCarbsGrams(),
                    saved.getFatGrams(),
                    saved.getDescription());

            return new HashMap<>() {
                {
                    put("message", "Nutrition plan created successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error creating nutrition plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
