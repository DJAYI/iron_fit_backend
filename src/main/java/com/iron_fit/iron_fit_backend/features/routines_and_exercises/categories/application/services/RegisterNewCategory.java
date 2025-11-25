package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.RegisterCategoryDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.responses.CategoryResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterNewCategory {
    @Autowired
    private CategoryRepository categoryRepository;

    public HashMap<String, Object> execute(RegisterCategoryDto registerCategoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(registerCategoryDto.name());

        try {

            CategoryEntity newCategory = categoryRepository.save(categoryEntity);
            return new HashMap<String, Object>() {{
                put("message", "Category has been registered successfully");
                put("category", new CategoryResponseDto(newCategory.getId(), newCategory.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Category could not be registered successfully");
                put("error", true);
            }};
        }
    }
}
