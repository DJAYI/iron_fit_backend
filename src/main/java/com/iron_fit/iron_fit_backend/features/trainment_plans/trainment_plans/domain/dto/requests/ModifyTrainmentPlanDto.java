package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ModifyTrainmentPlanDto(
        @NotNull(message = "Training plan ID is required") Long id,

        String name,

        String description,

        LocalDate startDate,

        LocalDate endDate,

        Long clientId,

        Long trainerId,

        Long objectiveId,

        Long stateId) {
}
