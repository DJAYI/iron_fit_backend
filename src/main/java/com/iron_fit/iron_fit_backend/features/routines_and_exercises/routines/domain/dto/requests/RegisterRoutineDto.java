package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRoutineDto(
        @NotBlank(message = "Routine name is required") String name,

        String description,

        @NotNull(message = "Training plan ID is required") Long trainmentPlanId) {
}
