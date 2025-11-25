package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services.ModifyRoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services.RegisterRoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.application.services.RetrieveRoutineExercises;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests.ModifyRoutineExerciseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.dto.requests.RegisterRoutineExerciseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/routine-exercises")
public class RoutineExerciseController {

    @Autowired
    private RegisterRoutineExercise registerRoutineExercise;

    @Autowired
    private ModifyRoutineExercise modifyRoutineExercise;

    @Autowired
    private RetrieveRoutineExercises retrieveRoutineExercises;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRoutineExerciseDto dto) {
        HashMap<String, Object> result = registerRoutineExercise.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping
    public ResponseEntity<?> modify(@Valid @RequestBody ModifyRoutineExerciseDto dto) {
        HashMap<String, Object> result = modifyRoutineExercise.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        HashMap<String, Object> result = retrieveRoutineExercises.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveRoutineExercises.execute(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/routine/{routineId}")
    public ResponseEntity<?> retrieveByRoutineId(@PathVariable Long routineId) {
        HashMap<String, Object> result = retrieveRoutineExercises.executeByRoutineId(routineId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<?> retrieveByExerciseId(@PathVariable Long exerciseId) {
        HashMap<String, Object> result = retrieveRoutineExercises.executeByExerciseId(exerciseId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
