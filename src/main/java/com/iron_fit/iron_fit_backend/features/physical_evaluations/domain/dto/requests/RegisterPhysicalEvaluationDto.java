package com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterPhysicalEvaluationDto(
                @NotNull(message = "Client ID is required") Long clientId,

                @NotNull(message = "Evaluation date is required") LocalDate evaluationDate,

                @NotNull(message = "Weight is required") @Positive(message = "Weight must be positive") BigDecimal weight,

                @NotNull(message = "BMI is required") @Positive(message = "BMI must be positive") BigDecimal bmi,

                @NotNull(message = "Body fat percentage is required") @Positive(message = "Body fat percentage must be positive") BigDecimal bodyFatPercentage,

                @NotNull(message = "Waist measurement is required") @Positive(message = "Waist measurement must be positive") BigDecimal waistMeasurement,

                @NotNull(message = "Hip measurement is required") @Positive(message = "Hip measurement must be positive") BigDecimal hipMeasurement,

                @NotNull(message = "Height measurement is required") @Positive(message = "Height measurement must be positive") BigDecimal heightMeasurement,

                String notes) {
}
