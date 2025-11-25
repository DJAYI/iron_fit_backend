package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services.ModifyTrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services.RegisterTrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services.RetrieveTrainmentPlans;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests.ModifyTrainmentPlanDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests.RegisterTrainmentPlanDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/training-plans")
public class TrainmentPlanController {

    @Autowired
    private RegisterTrainmentPlan registerTrainmentPlan;

    @Autowired
    private ModifyTrainmentPlan modifyTrainmentPlan;

    @Autowired
    private RetrieveTrainmentPlans retrieveTrainmentPlans;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterTrainmentPlanDto dto) {
        HashMap<String, Object> result = registerTrainmentPlan.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping
    public ResponseEntity<?> modify(@Valid @RequestBody ModifyTrainmentPlanDto dto) {
        HashMap<String, Object> result = modifyTrainmentPlan.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        HashMap<String, Object> result = retrieveTrainmentPlans.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveTrainmentPlans.execute(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> retrieveByClientId(@PathVariable Long clientId) {
        HashMap<String, Object> result = retrieveTrainmentPlans.executeByClientId(clientId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<?> retrieveByTrainerId(@PathVariable Long trainerId) {
        HashMap<String, Object> result = retrieveTrainmentPlans.executeByTrainerId(trainerId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
