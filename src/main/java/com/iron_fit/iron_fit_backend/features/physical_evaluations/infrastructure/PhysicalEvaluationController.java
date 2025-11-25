package com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure;

import com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services.ModifyPhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services.RegisterPhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services.RetrievePhysicalEvaluations;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests.ModifyPhysicalEvaluationDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests.RegisterPhysicalEvaluationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/physical-evaluations")
public class PhysicalEvaluationController {

    @Autowired
    private RegisterPhysicalEvaluation registerPhysicalEvaluation;

    @Autowired
    private ModifyPhysicalEvaluation modifyPhysicalEvaluation;

    @Autowired
    private RetrievePhysicalEvaluations retrievePhysicalEvaluations;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterPhysicalEvaluationDto dto) {
        HashMap<String, Object> result = registerPhysicalEvaluation.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@PathVariable Long id, @Valid @RequestBody ModifyPhysicalEvaluationDto dto) {
        HashMap<String, Object> result = modifyPhysicalEvaluation.execute(id, dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAll() {
        HashMap<String, Object> result = retrievePhysicalEvaluations.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        HashMap<String, Object> result = retrievePhysicalEvaluations.executeById(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getByClientId(@PathVariable Long clientId) {
        HashMap<String, Object> result = retrievePhysicalEvaluations.executeByClientId(clientId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<?> getByTrainerId(@PathVariable Long trainerId) {
        HashMap<String, Object> result = retrievePhysicalEvaluations.executeByTrainerId(trainerId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
