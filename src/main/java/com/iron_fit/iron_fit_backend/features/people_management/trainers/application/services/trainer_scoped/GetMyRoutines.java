package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.trainer_scoped;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.GetTrainerFromSession;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.responses.RoutineResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service("trainerGetMyRoutines")
public class GetMyRoutines {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private RoutineRepository routineRepository;

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

            List<Long> planIds = trainerPlans.stream()
                    .map(TrainmentPlan::getId)
                    .collect(Collectors.toList());

            List<Routine> routines = planIds.stream()
                    .flatMap(planId -> routineRepository.findByTrainmentPlanId(planId).stream())
                    .collect(Collectors.toList());

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
