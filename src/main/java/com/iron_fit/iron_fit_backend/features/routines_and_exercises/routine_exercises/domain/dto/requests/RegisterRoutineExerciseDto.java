package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterRoutineExerciseDto(
        @NotNull(message = "Routine ID is required") Long routineId,

        @NotNull(message = "Exercise ID is required") Long exerciseId,

        @NotNull(message = "Order is required") @Min(value = 1, message = "Order must be at least 1") Integer order,

        @NotNull(message = "Sets is required") @Min(value = 1, message = "Sets must be at least 1") Integer sets,

        @NotNull(message = "Reps is required") @Min(value = 1, message = "Reps must be at least 1") Integer reps,

        @Min(value = 0, message = "Time in seconds cannot be negative") Integer timeSeconds,

        @NotNull(message = "Rest in seconds is required") @Min(value = 0, message = "Rest in seconds cannot be negative") Integer restSeconds,

        @Min(value = 0, message = "Target weight cannot be negative") Double targetWeight) {
}
