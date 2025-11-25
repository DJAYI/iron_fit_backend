package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.responses.RoutineResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service("clientGetMyRoutines")
public class GetMyRoutines {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private RoutineRepository routineRepository;

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

            // Find active plan
            LocalDate today = LocalDate.now();
            TrainmentPlan activePlan = clientPlans.stream()
                    .filter(plan -> !today.isBefore(plan.getStartDate()) && !today.isAfter(plan.getEndDate()))
                    .findFirst()
                    .orElse(null);

            if (activePlan == null) {
                return new HashMap<>() {
                    {
                        put("message", "No active training plan found");
                        put("data", List.of());
                    }
                };
            }

            List<Routine> routines = routineRepository.findByTrainmentPlanId(activePlan.getId());

            List<RoutineResponseDto> responses = routines.stream()
                    .map(routine -> new RoutineResponseDto(
                            routine.getId(),
                            routine.getName(),
                            routine.getDescription(),
                            routine.getTrainmentPlan().getId(),
                            routine.getTrainmentPlan().getName()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Routines retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routines: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
