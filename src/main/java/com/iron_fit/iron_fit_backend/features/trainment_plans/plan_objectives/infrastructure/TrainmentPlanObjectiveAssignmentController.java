package com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services.DeleteAssignment;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services.RegisterAssignment;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.application.services.RetrieveAssignments;
import com.iron_fit.iron_fit_backend.features.trainment_plans.plan_objectives.domain.dto.requests.AssignObjectiveDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/plan-objectives")
public class TrainmentPlanObjectiveAssignmentController {

    @Autowired
    private RegisterAssignment registerAssignment;

    @Autowired
    private DeleteAssignment deleteAssignment;

    @Autowired
    private RetrieveAssignments retrieveAssignments;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> assign(@Valid @RequestBody AssignObjectiveDto dto) {
        HashMap<String, Object> result = registerAssignment.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        HashMap<String, Object> result = deleteAssignment.executeById(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @DeleteMapping("/unassign")
    public ResponseEntity<?> unassign(
            @RequestParam Long trainmentPlanId,
            @RequestParam Long objectiveId) {
        HashMap<String, Object> result = deleteAssignment.execute(trainmentPlanId, objectiveId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAll() {
        HashMap<String, Object> result = retrieveAssignments.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/training-plan/{trainmentPlanId}")
    public ResponseEntity<?> getByTrainingPlan(@PathVariable Long trainmentPlanId) {
        HashMap<String, Object> result = retrieveAssignments.executeByTrainmentPlanId(trainmentPlanId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/objective/{objectiveId}")
    public ResponseEntity<?> getByObjective(@PathVariable Long objectiveId) {
        HashMap<String, Object> result = retrieveAssignments.executeByObjectiveId(objectiveId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
