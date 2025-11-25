package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

public record ModifyRoutineDto(
        @NotNull(message = "Routine ID is required") Long id,

        String name,

        String description,

        Long trainmentPlanId) {
}
