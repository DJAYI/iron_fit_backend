package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.RegisterCategoryDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.UpdateCategoryDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.responses.CategoryResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service

public class ModifyExistingCategory {
    @Autowired
    private CategoryRepository categoryRepository;

    public HashMap<String, Object> execute(UpdateCategoryDto updateCategoryDto, Long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity.isEmpty()) return new HashMap<String, Object>() {{
            put("message", "Category with id " + categoryId + " not found");
            put("error", true);
        }};

        CategoryEntity newCategory = categoryEntity.get();
        newCategory.setName(updateCategoryDto.name());

        try {
            CategoryEntity updatedCategory = categoryRepository.save(newCategory);
            return new HashMap<String, Object>() {{
                put("message", "Category has been modified successfully");
                put("category", new CategoryResponseDto(newCategory.getId(), newCategory.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Category could not be modified successfully");
                put("error", true);
            }};
        }
    }
}
