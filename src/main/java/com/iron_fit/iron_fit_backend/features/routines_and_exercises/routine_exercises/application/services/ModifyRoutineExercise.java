package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository.ExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests.ModifyRoutineExerciseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.responses.RoutineExerciseResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository.RoutineExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class ModifyRoutineExercise {

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public HashMap<String, Object> execute(ModifyRoutineExerciseDto dto) {
        try {
            // Validate routine exercise exists
            Optional<RoutineExercise> routineExerciseOpt = routineExerciseRepository.findById(dto.id());
            if (routineExerciseOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine exercise not found with ID: " + dto.id());
                        put("error", true);
                    }
                };
            }

            RoutineExercise routineExercise = routineExerciseOpt.get();

            // Update routine if provided
            if (dto.routineId() != null) {
                Optional<Routine> routineOpt = routineRepository.findById(dto.routineId());
                if (routineOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Routine not found with ID: " + dto.routineId());
                            put("error", true);
                        }
                    };
                }
                routineExercise.setRoutine(routineOpt.get());
            }

            // Update exercise if provided
            if (dto.exerciseId() != null) {
                Optional<Exercise> exerciseOpt = exerciseRepository.findById(dto.exerciseId());
                if (exerciseOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Exercise not found with ID: " + dto.exerciseId());
                            put("error", true);
                        }
                    };
                }
                routineExercise.setExercise(exerciseOpt.get());
            }

            // Update order if provided
            if (dto.order() != null) {
                routineExercise.setOrder(dto.order());
            }

            // Update sets if provided
            if (dto.sets() != null) {
                routineExercise.setSets(dto.sets());
            }

            // Update reps if provided
            if (dto.reps() != null) {
                routineExercise.setReps(dto.reps());
            }

            // Update time seconds if provided
            if (dto.timeSeconds() != null) {
                routineExercise.setTimeSeconds(dto.timeSeconds());
            }

            // Update rest seconds if provided
            if (dto.restSeconds() != null) {
                routineExercise.setRestSeconds(dto.restSeconds());
            }

            // Update target weight if provided
            if (dto.targetWeight() != null) {
                routineExercise.setTargetWeight(dto.targetWeight());
            }

            // Save updated routine exercise
            RoutineExercise updatedRoutineExercise = routineExerciseRepository.save(routineExercise);

            // Create response DTO
            RoutineExerciseResponseDto responseDto = new RoutineExerciseResponseDto(
                    updatedRoutineExercise.getId(),
                    updatedRoutineExercise.getRoutine().getId(),
                    updatedRoutineExercise.getRoutine().getName(),
                    updatedRoutineExercise.getExercise().getId(),
                    updatedRoutineExercise.getExercise().getName(),
                    updatedRoutineExercise.getOrder(),
                    updatedRoutineExercise.getSets(),
                    updatedRoutineExercise.getReps(),
                    updatedRoutineExercise.getTimeSeconds(),
                    updatedRoutineExercise.getRestSeconds(),
                    updatedRoutineExercise.getTargetWeight());

            return new HashMap<>() {
                {
                    put("message", "Routine exercise modified successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error modifying routine exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
