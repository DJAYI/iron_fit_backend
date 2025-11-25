package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.infrastructure;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services.ModifyExistingCategory;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services.RegisterNewCategory;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.application.services.RetrieveCategories;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.RegisterCategoryDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.dto.requests.UpdateCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    @Autowired
    private ModifyExistingCategory modifyExistingCategory;

    @Autowired
    private RegisterNewCategory registerNewCategory;

    @Autowired
    protected RetrieveCategories retrieveCategories;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getCategories(){
        HashMap<String, Object> result = retrieveCategories.execute();

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> registerNewCategory(@RequestBody RegisterCategoryDto registerCategoryDto) {
        HashMap<String, Object> result = registerNewCategory.execute(registerCategoryDto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryDto updateCategoryDto) {
        HashMap<String, Object> result = modifyExistingCategory.execute(updateCategoryDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
