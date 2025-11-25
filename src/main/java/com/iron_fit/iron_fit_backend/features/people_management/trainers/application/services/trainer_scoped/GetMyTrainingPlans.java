package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.trainer_scoped;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.GetTrainerFromSession;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses.TrainmentPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("trainerGetMyTrainingPlans")
public class GetMyTrainingPlans {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private GetTrainerFromSession getTrainerFromSession;

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute() {
        try {
            Long authenticatedTrainerId = getTrainerFromSession.execute();

            if (authenticatedTrainerId == null) {
                return new HashMap<>() {
                    {
                        put("message", "Trainer not found in session");
                        put("error", true);
                    }
                };
            }

            List<TrainmentPlan> trainerPlans = trainmentPlanRepository.findByTrainerId(authenticatedTrainerId);

            List<TrainmentPlanResponseDto> responses = trainerPlans.stream()
                    .map(plan -> new TrainmentPlanResponseDto(
                            plan.getId(),
                            plan.getName(),
                            plan.getDescription(),
                            plan.getStartDate(),
                            plan.getEndDate(),
                            plan.getClient().getId(),
                            plan.getClient().getFirstName() + " " + plan.getClient().getLastName(),
                            (long) plan.getTrainer().getId(),
                            plan.getTrainer().getFirstName() + " " + plan.getTrainer().getLastName(),
                            plan.getObjective().getId(),
                            plan.getObjective().getName(),
                            plan.getState().getId(),
                            plan.getState().getName()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Training plans retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving training plans: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
