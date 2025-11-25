package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterExerciseDto(
        @NotBlank(message = "Exercise name is required") String name,

        String description,

        @NotNull(message = "Category ID is required") Long categoryId,

        @NotNull(message = "Muscular group ID is required") Long muscularGroupId) {
}
