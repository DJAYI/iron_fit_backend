package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services.ModifySession;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services.RegisterSession;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services.RetrieveSessions;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests.ModifySessionDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests.RegisterSessionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private RegisterSession registerSession;

    @Autowired
    private ModifySession modifySession;

    @Autowired
    private RetrieveSessions retrieveSessions;

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterSessionDto dto) {
        HashMap<String, Object> result = registerSession.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR') or hasRole('TRAINER')")
    @PutMapping
    public ResponseEntity<?> modify(@Valid @RequestBody ModifySessionDto dto) {
        HashMap<String, Object> result = modifySession.execute(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> retrieveAll() {
        HashMap<String, Object> result = retrieveSessions.execute();
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveById(@PathVariable Long id) {
        HashMap<String, Object> result = retrieveSessions.execute(id);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/routine/{routineId}")
    public ResponseEntity<?> retrieveByRoutineId(@PathVariable Long routineId) {
        HashMap<String, Object> result = retrieveSessions.executeByRoutineId(routineId);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/date-range")
    public ResponseEntity<?> retrieveByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        HashMap<String, Object> result = retrieveSessions.executeByDateRange(startDate, endDate);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
