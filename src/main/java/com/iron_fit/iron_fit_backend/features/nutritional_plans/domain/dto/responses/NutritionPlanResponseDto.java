package com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.responses;

import java.math.BigDecimal;

public record NutritionPlanResponseDto(
        Long id,
        Long trainmentPlanId,
        String trainmentPlanName,
        BigDecimal calories,
        BigDecimal proteinGrams,
        BigDecimal carbsGrams,
        BigDecimal fatGrams,
        String description) {
}
