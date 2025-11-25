package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.requests.AssignObjectiveDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.responses.AssignmentResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.entities.TrainmentPlanObjectiveAssignment;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.infrastructure.repository.TrainmentPlanObjectiveAssignmentRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterAssignment {

    @Autowired
    private TrainmentPlanObjectiveAssignmentRepository assignmentRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private TrainmentPlanObjectiveRepository objectiveRepository;

    public HashMap<String, Object> execute(AssignObjectiveDto dto) {
        try {
            // Validate training plan exists
            TrainmentPlan trainmentPlan = trainmentPlanRepository.findById(dto.trainmentPlanId())
                    .orElseThrow(() -> new RuntimeException("Training plan not found"));

            // Validate objective exists
            TrainmentPlanObjective objective = objectiveRepository.findById(dto.objectiveId())
                    .orElseThrow(() -> new RuntimeException("Objective not found"));

            // Check if assignment already exists
            if (assignmentRepository.existsByTrainmentPlanIdAndObjectiveId(
                    dto.trainmentPlanId(), dto.objectiveId())) {
                return new HashMap<>() {
                    {
                        put("message", "This objective is already assigned to the training plan");
                        put("error", true);
                    }
                };
            }

            // Create assignment
            TrainmentPlanObjectiveAssignment assignment = TrainmentPlanObjectiveAssignment.builder()
                    .trainmentPlan(trainmentPlan)
                    .objective(objective)
                    .build();

            TrainmentPlanObjectiveAssignment saved = assignmentRepository.save(assignment);

            AssignmentResponseDto response = new AssignmentResponseDto(
                    saved.getId(),
                    saved.getTrainmentPlan().getId(),
                    saved.getTrainmentPlan().getName(),
                    saved.getObjective().getId(),
                    saved.getObjective().getName());

            return new HashMap<>() {
                {
                    put("message", "Objective assigned successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error assigning objective: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
