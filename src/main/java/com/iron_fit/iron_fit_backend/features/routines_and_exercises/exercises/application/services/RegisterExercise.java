package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests.RegisterExerciseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.responses.ExerciseResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure.repository.ExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository.MuscularGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RegisterExercise {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MuscularGroupRepository muscularGroupRepository;

    public HashMap<String, Object> execute(RegisterExerciseDto dto) {
        try {
            // Validate category exists
            Optional<CategoryEntity> categoryOpt = categoryRepository.findById(dto.categoryId());
            if (categoryOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Category not found with ID: " + dto.categoryId());
                        put("error", true);
                    }
                };
            }

            // Validate muscular group exists
            Optional<MuscularGroupEntity> muscularGroupOpt = muscularGroupRepository.findById(dto.muscularGroupId());
            if (muscularGroupOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Muscular group not found with ID: " + dto.muscularGroupId());
                        put("error", true);
                    }
                };
            }

            // Create exercise entity
            Exercise exercise = Exercise.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .category(categoryOpt.get())
                    .muscularGroup(muscularGroupOpt.get())
                    .build();

            // Save exercise
            Exercise savedExercise = exerciseRepository.save(exercise);

            // Create response DTO
            ExerciseResponseDto responseDto = new ExerciseResponseDto(
                    savedExercise.getId(),
                    savedExercise.getName(),
                    savedExercise.getDescription(),
                    savedExercise.getCategory().getId(),
                    savedExercise.getCategory().getName(),
                    savedExercise.getMuscularGroup().getId(),
                    savedExercise.getMuscularGroup().getName());

            return new HashMap<>() {
                {
                    put("message", "Exercise registered successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error registering exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
