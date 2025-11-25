package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.responses;

public record RoutineResponseDto(
        Long id,
        String name,
        String description,
        Long trainmentPlanId,
        String trainmentPlanName) {
}
