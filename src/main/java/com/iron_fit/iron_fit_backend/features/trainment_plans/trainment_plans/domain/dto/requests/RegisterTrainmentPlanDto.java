package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterTrainmentPlanDto(
        @NotBlank(message = "Training plan name is required") String name,

        String description,

        @NotNull(message = "Start date is required") LocalDate startDate,

        LocalDate endDate,

        @NotNull(message = "Client ID is required") Long clientId,

        @NotNull(message = "Trainer ID is required") Long trainerId,

        @NotNull(message = "Objective ID is required") Long objectiveId,

        @NotNull(message = "State ID is required") Long stateId) {
}
