package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses;

import java.time.LocalDate;

public record TrainmentPlanResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Long clientId,
        String clientName,
        Long trainerId,
        String trainerName,
        Long objectiveId,
        String objectiveName,
        Long stateId,
        String stateName) {
}
