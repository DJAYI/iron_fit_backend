package com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ModifyNutritionPlanDto(
        Long trainmentPlanId,

        @Positive(message = "Calories must be positive") BigDecimal calories,

        @Positive(message = "Protein grams must be positive") BigDecimal proteinGrams,

        @Positive(message = "Carbohydrate grams must be positive") BigDecimal carbsGrams,

        @Positive(message = "Fat grams must be positive") BigDecimal fatGrams,

        String description) {
}
