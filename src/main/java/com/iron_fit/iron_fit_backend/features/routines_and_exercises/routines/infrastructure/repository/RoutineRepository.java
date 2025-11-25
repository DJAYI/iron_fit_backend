package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findByTrainmentPlanId(Long trainmentPlanId);
}
