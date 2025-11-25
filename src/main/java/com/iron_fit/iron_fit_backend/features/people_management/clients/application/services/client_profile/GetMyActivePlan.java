package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses.TrainmentPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service("clientGetMyActivePlan")
public class GetMyActivePlan {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute() {
        try {
            Long authenticatedClientId = getClientFromSession.execute();

            if (authenticatedClientId == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found in session");
                        put("error", true);
                    }
                };
            }

            List<TrainmentPlan> clientPlans = trainmentPlanRepository.findByClientId(authenticatedClientId);

            // Find active plan (current date is between start and end date)
            LocalDate today = LocalDate.now();
            TrainmentPlan activePlan = clientPlans.stream()
                    .filter(plan -> !today.isBefore(plan.getStartDate()) && !today.isAfter(plan.getEndDate()))
                    .findFirst()
                    .orElse(null);

            if (activePlan == null) {
                return new HashMap<>() {
                    {
                        put("message", "No active training plan found");
                        put("data", null);
                    }
                };
            }

            TrainmentPlanResponseDto response = new TrainmentPlanResponseDto(
                    activePlan.getId(),
                    activePlan.getName(),
                    activePlan.getDescription(),
                    activePlan.getStartDate(),
                    activePlan.getEndDate(),
                    activePlan.getClient().getId(),
                    activePlan.getClient().getFirstName() + " " + activePlan.getClient().getLastName(),
                    (long) activePlan.getTrainer().getId(),
                    activePlan.getTrainer().getFirstName() + " " + activePlan.getTrainer().getLastName(),
                    activePlan.getObjective().getId(),
                    activePlan.getObjective().getName(),
                    activePlan.getState().getId(),
                    activePlan.getState().getName());

            return new HashMap<>() {
                {
                    put("message", "Active plan retrieved successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving active plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
