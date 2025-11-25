package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ModifyRoutineExerciseDto(
        @NotNull(message = "Routine exercise ID is required") Long id,

        Long routineId,

        Long exerciseId,

        @Min(value = 1, message = "Order must be at least 1") Integer order,

        @Min(value = 1, message = "Sets must be at least 1") Integer sets,

        @Min(value = 1, message = "Reps must be at least 1") Integer reps,

        @Min(value = 0, message = "Time in seconds cannot be negative") Integer timeSeconds,

        @Min(value = 0, message = "Rest in seconds cannot be negative") Integer restSeconds,

        @Min(value = 0, message = "Target weight cannot be negative") Double targetWeight) {
}
