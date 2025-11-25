package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.infrastructure;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services.ModifyExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services.RegisterExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.application.services.RetrieveExercises;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests.ModifyExerciseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.dto.requests.RegisterExerciseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private RegisterExercise registerExercise;

    @Autowired
    private ModifyExercise modifyExercise;

    @Autowired
    private RetrieveExercises retrieveExercises;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterExerciseDto dto) {
        HashMap<String, Object> result = registerExercise.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping
    public ResponseEntity<?> modify(@Valid @RequestBody ModifyExerciseDto dto) {
        HashMap<String, Object> result = modifyExercise.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        HashMap<String, Object> result = retrieveExercises.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveExercises.execute(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
