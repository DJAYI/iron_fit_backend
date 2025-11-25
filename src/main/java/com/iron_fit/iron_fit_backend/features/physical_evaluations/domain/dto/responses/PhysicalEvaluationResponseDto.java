package com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PhysicalEvaluationResponseDto(
        Long id,
        Long clientId,
        String clientName,
        Long trainerId,
        String trainerName,
        LocalDate evaluationDate,
        BigDecimal weight,
        BigDecimal bmi,
        BigDecimal bodyFatPercentage,
        BigDecimal waistMeasurement,
        BigDecimal hipMeasurement,
        BigDecimal heightMeasurement,
        String notes) {
}
