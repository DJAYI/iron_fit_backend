package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests.RegisterRoutineDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.responses.RoutineResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RegisterRoutine {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    public HashMap<String, Object> execute(RegisterRoutineDto dto) {
        try {
            // Validate training plan exists
            Optional<TrainmentPlan> planOpt = trainmentPlanRepository.findById(dto.trainmentPlanId());
            if (planOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Training plan not found with ID: " + dto.trainmentPlanId());
                        put("error", true);
                    }
                };
            }

            // Create routine entity
            Routine routine = Routine.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .trainmentPlan(planOpt.get())
                    .build();

            // Save routine
            Routine savedRoutine = routineRepository.save(routine);

            // Create response DTO
            RoutineResponseDto responseDto = new RoutineResponseDto(
                    savedRoutine.getId(),
                    savedRoutine.getName(),
                    savedRoutine.getDescription(),
                    savedRoutine.getTrainmentPlan().getId(),
                    savedRoutine.getTrainmentPlan().getName());

            return new HashMap<>() {
                {
                    put("message", "Routine registered successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error registering routine: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
