package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.responses.AssignmentResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.entities.TrainmentPlanObjectiveAssignment;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.infrastructure.repository.TrainmentPlanObjectiveAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveAssignments {

    @Autowired
    private TrainmentPlanObjectiveAssignmentRepository assignmentRepository;

    public HashMap<String, Object> execute() {
        try {
            List<TrainmentPlanObjectiveAssignment> assignments = assignmentRepository.findAll();

            List<AssignmentResponseDto> responses = assignments.stream()
                    .map(assignment -> new AssignmentResponseDto(
                            assignment.getId(),
                            assignment.getTrainmentPlan().getId(),
                            assignment.getTrainmentPlan().getName(),
                            assignment.getObjective().getId(),
                            assignment.getObjective().getName()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Assignments retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving assignments: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByTrainmentPlanId(Long trainmentPlanId) {
        try {
            List<TrainmentPlanObjectiveAssignment> assignments = assignmentRepository
                    .findByTrainmentPlanId(trainmentPlanId);

            List<AssignmentResponseDto> responses = assignments.stream()
                    .map(assignment -> new AssignmentResponseDto(
                            assignment.getId(),
                            assignment.getTrainmentPlan().getId(),
                            assignment.getTrainmentPlan().getName(),
                            assignment.getObjective().getId(),
                            assignment.getObjective().getName()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Assignments retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving assignments: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByObjectiveId(Long objectiveId) {
        try {
            List<TrainmentPlanObjectiveAssignment> assignments = assignmentRepository.findByObjectiveId(objectiveId);

            List<AssignmentResponseDto> responses = assignments.stream()
                    .map(assignment -> new AssignmentResponseDto(
                            assignment.getId(),
                            assignment.getTrainmentPlan().getId(),
                            assignment.getTrainmentPlan().getName(),
                            assignment.getObjective().getId(),
                            assignment.getObjective().getName()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Assignments retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving assignments: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
