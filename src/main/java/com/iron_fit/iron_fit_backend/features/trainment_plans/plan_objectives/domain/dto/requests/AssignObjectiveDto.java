package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

public record AssignObjectiveDto(
        @NotNull(message = "Training plan ID is required") Long trainmentPlanId,

        @NotNull(message = "Objective ID is required") Long objectiveId) {
}
