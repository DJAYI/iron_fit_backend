package com.iron_fit.iron_fit_backend.features.nutritional_plans.infrastructure;

import com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services.ModifyNutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services.RegisterNutritionPlan;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.application.services.RetrieveNutritionPlans;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests.ModifyNutritionPlanDto;
import com.iron_fit.iron_fit_backend.features.nutritional_plans.domain.dto.requests.RegisterNutritionPlanDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/nutrition-plans")
public class NutritionPlanController {

    @Autowired
    private RegisterNutritionPlan registerNutritionPlan;

    @Autowired
    private ModifyNutritionPlan modifyNutritionPlan;

    @Autowired
    private RetrieveNutritionPlans retrieveNutritionPlans;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterNutritionPlanDto dto) {
        HashMap<String, Object> result = registerNutritionPlan.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@PathVariable Long id, @Valid @RequestBody ModifyNutritionPlanDto dto) {
        HashMap<String, Object> result = modifyNutritionPlan.execute(id, dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAll() {
        HashMap<String, Object> result = retrieveNutritionPlans.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveNutritionPlans.executeById(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/training-plan/{trainmentPlanId}")
    public ResponseEntity<?> getByTrainmentPlanId(@PathVariable Long trainmentPlanId) {
        HashMap<String, Object> result = retrieveNutritionPlans.executeByTrainmentPlanId(trainmentPlanId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
