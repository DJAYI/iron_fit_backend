package com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ModifyPhysicalEvaluationDto(
        Long clientId,
        Long trainerId,
        LocalDate evaluationDate,

        @Positive(message = "Weight must be positive") BigDecimal weight,

        @Positive(message = "BMI must be positive") BigDecimal bmi,

        @Positive(message = "Body fat percentage must be positive") BigDecimal bodyFatPercentage,

        @Positive(message = "Waist measurement must be positive") BigDecimal waistMeasurement,

        @Positive(message = "Hip measurement must be positive") BigDecimal hipMeasurement,

        @Positive(message = "Height measurement must be positive") BigDecimal heightMeasurement,

        String notes) {
}
