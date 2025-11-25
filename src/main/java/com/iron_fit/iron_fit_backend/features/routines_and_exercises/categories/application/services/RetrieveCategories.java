package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.RegisterCategoryDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.responses.CategoryResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveCategories {
    @Autowired
    private CategoryRepository categoryRepository;

    public HashMap<String, Object> execute() {
        try {

            List<CategoryEntity> categoriesEntities = categoryRepository.findAll();

            List<CategoryResponseDto> mappedCategories = categoriesEntities.stream().map(categoryEntity -> new CategoryResponseDto(categoryEntity.getId(), categoryEntity.getName())).toList();
            return new HashMap<String, Object>() {{
                put("message", "Categories has been retrieve successfully");
                put("categories", mappedCategories);
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Categories could not be retrieve");
                put("error", true);
            }};
        }
    }
}
