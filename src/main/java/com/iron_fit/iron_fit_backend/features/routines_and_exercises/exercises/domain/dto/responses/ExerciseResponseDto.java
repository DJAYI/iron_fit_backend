package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.responses;

public record ExerciseResponseDto(
        Long id,
        String name,
        String description,
        Long categoryId,
        String categoryName,
        Long muscularGroupId,
        String muscularGroupName) {
}
