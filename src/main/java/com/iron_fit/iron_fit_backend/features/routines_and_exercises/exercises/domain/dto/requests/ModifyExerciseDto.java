package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

public record ModifyExerciseDto(
        @NotNull(message = "Exercise ID is required") Long id,

        String name,

        String description,

        Long categoryId,

        Long muscularGroupId) {
}
