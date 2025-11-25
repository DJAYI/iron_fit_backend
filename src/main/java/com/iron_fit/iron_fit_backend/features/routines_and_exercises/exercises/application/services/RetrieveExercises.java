package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.responses.ExerciseResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetrieveExercises {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public HashMap<String, Object> execute() {
        try {
            List<Exercise> exercises = exerciseRepository.findAll();

            List<ExerciseResponseDto> responseDtos = exercises.stream()
                    .map(exercise -> new ExerciseResponseDto(
                            exercise.getId(),
                            exercise.getName(),
                            exercise.getDescription(),
                            exercise.getCategory().getId(),
                            exercise.getCategory().getName(),
                            exercise.getMuscularGroup().getId(),
                            exercise.getMuscularGroup().getName()))
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Exercises retrieved successfully");
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving exercises: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> execute(Long id) {
        try {
            Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);

            if (exerciseOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Exercise not found with ID: " + id);
                        put("error", true);
                    }
                };
            }

            Exercise exercise = exerciseOpt.get();
            ExerciseResponseDto responseDto = new ExerciseResponseDto(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getDescription(),
                    exercise.getCategory().getId(),
                    exercise.getCategory().getName(),
                    exercise.getMuscularGroup().getId(),
                    exercise.getMuscularGroup().getName());

            return new HashMap<>() {
                {
                    put("message", "Exercise retrieved successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
