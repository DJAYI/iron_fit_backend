package com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalEvaluationRepository extends JpaRepository<PhysicalEvaluation, Long> {
    List<PhysicalEvaluation> findByClientId(Long clientId);

    List<PhysicalEvaluation> findByTrainerId(Long trainerId);

    List<PhysicalEvaluation> findByClientIdOrderByEvaluationDateDesc(Long clientId);

    List<PhysicalEvaluation> findByEvaluationDateBetween(LocalDate startDate, LocalDate endDate);
}
