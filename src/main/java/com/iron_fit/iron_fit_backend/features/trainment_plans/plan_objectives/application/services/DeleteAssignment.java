package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.entities.TrainmentPlanObjectiveAssignment;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.infrastructure.repository.TrainmentPlanObjectiveAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DeleteAssignment {

    @Autowired
    private TrainmentPlanObjectiveAssignmentRepository assignmentRepository;

    public HashMap<String, Object> execute(Long trainmentPlanId, Long objectiveId) {
        try {
            // Find assignment
            TrainmentPlanObjectiveAssignment assignment = assignmentRepository.findByTrainmentPlanIdAndObjectiveId(
                    trainmentPlanId, objectiveId)
                    .orElseThrow(() -> new RuntimeException("Assignment not found"));

            assignmentRepository.delete(assignment);

            return new HashMap<>() {
                {
                    put("message", "Objective unassigned successfully");
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error unassigning objective: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeById(Long id) {
        try {
            // Validate assignment exists
            if (!assignmentRepository.existsById(id)) {
                return new HashMap<>() {
                    {
                        put("message", "Assignment not found");
                        put("error", true);
                    }
                };
            }

            assignmentRepository.deleteById(id);

            return new HashMap<>() {
                {
                    put("message", "Assignment deleted successfully");
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error deleting assignment: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
