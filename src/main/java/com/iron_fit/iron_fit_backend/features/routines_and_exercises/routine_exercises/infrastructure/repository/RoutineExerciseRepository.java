package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
    List<RoutineExercise> findByRoutineIdOrderByOrderAsc(Long routineId);

    List<RoutineExercise> findByExerciseId(Long exerciseId);
}
