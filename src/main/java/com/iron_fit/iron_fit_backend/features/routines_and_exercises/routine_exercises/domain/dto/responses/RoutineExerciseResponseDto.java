package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.responses;

public record RoutineExerciseResponseDto(
        Long id,
        Long routineId,
        String routineName,
        Long exerciseId,
        String exerciseName,
        Integer order,
        Integer sets,
        Integer reps,
        Integer timeSeconds,
        Integer restSeconds,
        Double targetWeight) {
}
