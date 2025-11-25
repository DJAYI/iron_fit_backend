package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscularGroupRepository extends JpaRepository<MuscularGroupEntity, Long> {
}
