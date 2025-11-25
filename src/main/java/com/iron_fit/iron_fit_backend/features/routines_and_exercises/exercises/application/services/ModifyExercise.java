package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests.ModifyExerciseDto;
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
public class ModifyExercise {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MuscularGroupRepository muscularGroupRepository;

    public HashMap<String, Object> execute(ModifyExerciseDto dto) {
        try {
            // Validate exercise exists
            Optional<Exercise> exerciseOpt = exerciseRepository.findById(dto.id());
            if (exerciseOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Exercise not found with ID: " + dto.id());
                        put("error", true);
                    }
                };
            }

            Exercise exercise = exerciseOpt.get();

            // Update name if provided
            if (dto.name() != null && !dto.name().isBlank()) {
                exercise.setName(dto.name());
            }

            // Update description if provided
            if (dto.description() != null) {
                exercise.setDescription(dto.description());
            }

            // Update category if provided
            if (dto.categoryId() != null) {
                Optional<CategoryEntity> categoryOpt = categoryRepository.findById(dto.categoryId());
                if (categoryOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Category not found with ID: " + dto.categoryId());
                            put("error", true);
                        }
                    };
                }
                exercise.setCategory(categoryOpt.get());
            }

            // Update muscular group if provided
            if (dto.muscularGroupId() != null) {
                Optional<MuscularGroupEntity> muscularGroupOpt = muscularGroupRepository
                        .findById(dto.muscularGroupId());
                if (muscularGroupOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Muscular group not found with ID: " + dto.muscularGroupId());
                            put("error", true);
                        }
                    };
                }
                exercise.setMuscularGroup(muscularGroupOpt.get());
            }

            // Save updated exercise
            Exercise updatedExercise = exerciseRepository.save(exercise);

            // Create response DTO
            ExerciseResponseDto responseDto = new ExerciseResponseDto(
                    updatedExercise.getId(),
                    updatedExercise.getName(),
                    updatedExercise.getDescription(),
                    updatedExercise.getCategory().getId(),
                    updatedExercise.getCategory().getName(),
                    updatedExercise.getMuscularGroup().getId(),
                    updatedExercise.getMuscularGroup().getName());

            return new HashMap<>() {
                {
                    put("message", "Exercise modified successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error modifying exercise: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
