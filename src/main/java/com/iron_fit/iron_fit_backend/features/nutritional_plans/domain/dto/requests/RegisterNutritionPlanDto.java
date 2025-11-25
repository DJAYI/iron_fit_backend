package com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegisterNutritionPlanDto(
        @NotNull(message = "Training plan ID is required") Long trainmentPlanId,

        @NotNull(message = "Calories are required") @Positive(message = "Calories must be positive") BigDecimal calories,

        @NotNull(message = "Protein grams are required") @Positive(message = "Protein grams must be positive") BigDecimal proteinGrams,

        @NotNull(message = "Carbohydrate grams are required") @Positive(message = "Carbohydrate grams must be positive") BigDecimal carbsGrams,

        @NotNull(message = "Fat grams are required") @Positive(message = "Fat grams must be positive") BigDecimal fatGrams,

        String description) {
}
