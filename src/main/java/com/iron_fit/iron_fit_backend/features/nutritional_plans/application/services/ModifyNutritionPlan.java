package com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services;

import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests.ModifyNutritionPlanDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.responses.NutritionPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities.NutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure.repository.NutritionPlanRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ModifyNutritionPlan {

    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    public HashMap<String, Object> execute(Long id, ModifyNutritionPlanDto dto) {
        try {
            // Validate nutrition plan exists
            NutritionPlan nutritionPlan = nutritionPlanRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Nutrition plan not found"));

            // Update training plan if provided
            if (dto.trainmentPlanId() != null) {
                TrainmentPlan trainmentPlan = trainmentPlanRepository.findById(dto.trainmentPlanId())
                        .orElseThrow(() -> new RuntimeException("Training plan not found"));
                nutritionPlan.setTrainmentPlan(trainmentPlan);
            }

            // Update other fields if provided
            if (dto.calories() != null) {
                nutritionPlan.setCalories(dto.calories());
            }
            if (dto.proteinGrams() != null) {
                nutritionPlan.setProteinGrams(dto.proteinGrams());
            }
            if (dto.carbsGrams() != null) {
                nutritionPlan.setCarbsGrams(dto.carbsGrams());
            }
            if (dto.fatGrams() != null) {
                nutritionPlan.setFatGrams(dto.fatGrams());
            }
            if (dto.description() != null) {
                nutritionPlan.setDescription(dto.description());
            }

            NutritionPlan updated = nutritionPlanRepository.save(nutritionPlan);

            NutritionPlanResponseDto response = new NutritionPlanResponseDto(
                    updated.getId(),
                    updated.getTrainmentPlan().getId(),
                    updated.getTrainmentPlan().getName(),
                    updated.getCalories(),
                    updated.getProteinGrams(),
                    updated.getCarbsGrams(),
                    updated.getFatGrams(),
                    updated.getDescription());

            return new HashMap<>() {
                {
                    put("message", "Nutrition plan updated successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error updating nutrition plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
