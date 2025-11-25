package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.responses.RoutineExerciseResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository.RoutineExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetrieveRoutineExercises {

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    public HashMap<String, Object> execute() {
        try {
            List<RoutineExercise> routineExercises = routineExerciseRepository.findAll();

            List<RoutineExerciseResponseDto> responseDtos = routineExercises.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Routine exercises retrieved successfully");
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routine exercises: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> execute(Long id) {
        try {
            Optional<RoutineExercise> routineExerciseOpt = routineExerciseRepository.findById(id);

            if (routineExerciseOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine exercise not found with ID: " + id);
                        put("error", true);
                    }
                };
            }

            RoutineExerciseResponseDto responseDto = mapToResponseDto(routineExerciseOpt.get());

            return new HashMap<>() {
                {
                    put("message", "Routine exercise retrieved successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routine exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByRoutineId(Long routineId) {
        try {
            List<RoutineExercise> routineExercises = routineExerciseRepository
                    .findByRoutineIdOrderByOrderAsc(routineId);

            List<RoutineExerciseResponseDto> responseDtos = routineExercises.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Routine exercises retrieved successfully for routine ID: " + routineId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routine exercises by routine: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByExerciseId(Long exerciseId) {
        try {
            List<RoutineExercise> routineExercises = routineExerciseRepository.findByExerciseId(exerciseId);

            List<RoutineExerciseResponseDto> responseDtos = routineExercises.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Routine exercises retrieved successfully for exercise ID: " + exerciseId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving routine exercises by exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private RoutineExerciseResponseDto mapToResponseDto(RoutineExercise routineExercise) {
        return new RoutineExerciseResponseDto(
                routineExercise.getId(),
                routineExercise.getRoutine().getId(),
                routineExercise.getRoutine().getName(),
                routineExercise.getExercise().getId(),
                routineExercise.getExercise().getName(),
                routineExercise.getOrder(),
                routineExercise.getSets(),
                routineExercise.getReps(),
                routineExercise.getTimeSeconds(),
                routineExercise.getRestSeconds(),
                routineExercise.getTargetWeight());
    }
}
