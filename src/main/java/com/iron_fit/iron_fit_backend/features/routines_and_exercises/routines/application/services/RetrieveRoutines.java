package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.responses.RoutineResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetrieveRoutines {

    @Autowired
    private RoutineRepository routineRepository;

    public HashMap<String, Object> execute() {
        try {
            List<Routine> routines = routineRepository.findAll();

            List<RoutineResponseDto> responseDtos = routines.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Routines retrieved successfully");
                    put("data", responseDtos);
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

    public HashMap<String, Object> execute(Long id) {
        try {
            Optional<Routine> routineOpt = routineRepository.findById(id);

            if (routineOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine not found with ID: " + id);
                        put("error", true);
                    }
                };
            }

            RoutineResponseDto responseDto = mapToResponseDto(routineOpt.get());

            return new HashMap<>() {
                {
                    put("message", "Routine retrieved successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routine: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByTrainmentPlanId(Long trainmentPlanId) {
        try {
            List<Routine> routines = routineRepository.findByTrainmentPlanId(trainmentPlanId);

            List<RoutineResponseDto> responseDtos = routines.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Routines retrieved successfully for training plan ID: " + trainmentPlanId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routines by training plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private RoutineResponseDto mapToResponseDto(Routine routine) {
        return new RoutineResponseDto(
                routine.getId(),
                routine.getName(),
                routine.getDescription(),
                routine.getTrainmentPlan().getId(),
                routine.getTrainmentPlan().getName());
    }
}
