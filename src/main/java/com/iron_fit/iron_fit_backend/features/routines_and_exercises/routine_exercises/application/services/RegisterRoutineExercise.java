package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository.ExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests.RegisterRoutineExerciseDto;
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
public class RegisterRoutineExercise {

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public HashMap<String, Object> execute(RegisterRoutineExerciseDto dto) {
        try {
            // Validate routine exists
            Optional<Routine> routineOpt = routineRepository.findById(dto.routineId());
            if (routineOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine not found with ID: " + dto.routineId());
                        put("error", true);
                    }
                };
            }

            // Validate exercise exists
            Optional<Exercise> exerciseOpt = exerciseRepository.findById(dto.exerciseId());
            if (exerciseOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Exercise not found with ID: " + dto.exerciseId());
                        put("error", true);
                    }
                };
            }

            // Create routine exercise entity
            RoutineExercise routineExercise = RoutineExercise.builder()
                    .routine(routineOpt.get())
                    .exercise(exerciseOpt.get())
                    .order(dto.order())
                    .sets(dto.sets())
                    .reps(dto.reps())
                    .timeSeconds(dto.timeSeconds())
                    .restSeconds(dto.restSeconds())
                    .targetWeight(dto.targetWeight())
                    .build();

            // Save routine exercise
            RoutineExercise savedRoutineExercise = routineExerciseRepository.save(routineExercise);

            // Create response DTO
            RoutineExerciseResponseDto responseDto = new RoutineExerciseResponseDto(
                    savedRoutineExercise.getId(),
                    savedRoutineExercise.getRoutine().getId(),
                    savedRoutineExercise.getRoutine().getName(),
                    savedRoutineExercise.getExercise().getId(),
                    savedRoutineExercise.getExercise().getName(),
                    savedRoutineExercise.getOrder(),
                    savedRoutineExercise.getSets(),
                    savedRoutineExercise.getReps(),
                    savedRoutineExercise.getTimeSeconds(),
                    savedRoutineExercise.getRestSeconds(),
                    savedRoutineExercise.getTargetWeight());

            return new HashMap<>() {
                {
                    put("message", "Routine exercise registered successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error registering routine exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
