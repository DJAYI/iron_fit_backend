package com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services;

import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.responses.NutritionPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.entities.NutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure.repository.NutritionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveNutritionPlans {

    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;

    public HashMap<String, Object> execute() {
        try {
            List<NutritionPlan> nutritionPlans = nutritionPlanRepository.findAll();

            List<NutritionPlanResponseDto> responses = nutritionPlans.stream()
                    .map(this::mapToResponseDto)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Nutrition plans retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving nutrition plans: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeById(Long id) {
        try {
            NutritionPlan nutritionPlan = nutritionPlanRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Nutrition plan not found"));

            NutritionPlanResponseDto response = mapToResponseDto(nutritionPlan);

            return new HashMap<>() {
                {
                    put("message", "Nutrition plan retrieved successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving nutrition plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByTrainmentPlanId(Long trainmentPlanId) {
        try {
            List<NutritionPlan> nutritionPlans = nutritionPlanRepository.findByTrainmentPlanId(trainmentPlanId);

            List<NutritionPlanResponseDto> responses = nutritionPlans.stream()
                    .map(this::mapToResponseDto)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Nutrition plans retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving nutrition plans: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private NutritionPlanResponseDto mapToResponseDto(NutritionPlan nutritionPlan) {
        return new NutritionPlanResponseDto(
                nutritionPlan.getId(),
                nutritionPlan.getTrainmentPlan().getId(),
                nutritionPlan.getTrainmentPlan().getName(),
                nutritionPlan.getCalories(),
                nutritionPlan.getProteinGrams(),
                nutritionPlan.getCarbsGrams(),
                nutritionPlan.getFatGrams(),
                nutritionPlan.getDescription());
    }
}
