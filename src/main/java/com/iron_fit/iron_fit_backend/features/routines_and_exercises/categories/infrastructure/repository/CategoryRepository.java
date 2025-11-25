package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
