package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests.ModifyRoutineDto;
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
public class ModifyRoutine {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    public HashMap<String, Object> execute(ModifyRoutineDto dto) {
        try {
            // Validate routine exists
            Optional<Routine> routineOpt = routineRepository.findById(dto.id());
            if (routineOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine not found with ID: " + dto.id());
                        put("error", true);
                    }
                };
            }

            Routine routine = routineOpt.get();

            // Update name if provided
            if (dto.name() != null && !dto.name().isBlank()) {
                routine.setName(dto.name());
            }

            // Update description if provided
            if (dto.description() != null) {
                routine.setDescription(dto.description());
            }

            // Update training plan if provided
            if (dto.trainmentPlanId() != null) {
                Optional<TrainmentPlan> planOpt = trainmentPlanRepository.findById(dto.trainmentPlanId());
                if (planOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Training plan not found with ID: " + dto.trainmentPlanId());
                            put("error", true);
                        }
                    };
                }
                routine.setTrainmentPlan(planOpt.get());
            }

            // Save updated routine
            Routine updatedRoutine = routineRepository.save(routine);

            // Create response DTO
            RoutineResponseDto responseDto = new RoutineResponseDto(
                    updatedRoutine.getId(),
                    updatedRoutine.getName(),
                    updatedRoutine.getDescription(),
                    updatedRoutine.getTrainmentPlan().getId(),
                    updatedRoutine.getTrainmentPlan().getName());

            return new HashMap<>() {
                {
                    put("message", "Routine modified successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error modifying routine: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
