package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
