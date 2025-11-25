package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services.ModifyRoutine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services.RegisterRoutine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.application.services.RetrieveRoutines;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests.ModifyRoutineDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.dto.requests.RegisterRoutineDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    @Autowired
    private RegisterRoutine registerRoutine;

    @Autowired
    private ModifyRoutine modifyRoutine;

    @Autowired
    private RetrieveRoutines retrieveRoutines;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRoutineDto dto) {
        HashMap<String, Object> result = registerRoutine.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping
    public ResponseEntity<?> modify(@Valid @RequestBody ModifyRoutineDto dto) {
        HashMap<String, Object> result = modifyRoutine.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        HashMap<String, Object> result = retrieveRoutines.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveRoutines.execute(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/training-plan/{trainmentPlanId}")
    public ResponseEntity<?> retrieveByTrainmentPlanId(@PathVariable Long trainmentPlanId) {
        HashMap<String, Object> result = retrieveRoutines.executeByTrainmentPlanId(trainmentPlanId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
