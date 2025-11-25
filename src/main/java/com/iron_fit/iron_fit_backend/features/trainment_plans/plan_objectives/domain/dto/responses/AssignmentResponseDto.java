package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.responses;

public record AssignmentResponseDto(
        Long id,
        Long trainmentPlanId,
        String trainmentPlanName,
        Long objectiveId,
        String objectiveName) {
}
