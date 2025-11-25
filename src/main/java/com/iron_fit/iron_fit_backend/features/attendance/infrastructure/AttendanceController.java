package com.iron_fit.iron_fit_backend.features.attendance.infrastructure;

import com.iron_fit.iron_fit_backend.features.attendance.application.services.RegisterClientAttendance;
import com.iron_fit.iron_fit_backend.features.attendance.application.services.RetrieveAllAttendance;
import com.iron_fit.iron_fit_backend.features.attendance.application.services.RetrieveClientAttendance;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private RegisterClientAttendance registerClientAttendance;

    @Autowired
    private RetrieveClientAttendance retrieveClientAttendance;

    @Autowired
    private RetrieveAllAttendance retrieveAllAttendance;

    @PostMapping
    public ResponseEntity<?> registerClientAttendance() {
        HashMap<String, Object> result = registerClientAttendance.execute();

        if (Objects.nonNull(result.get("error"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<?> getClientAttendance() {
        HashMap<String, Object> result = retrieveClientAttendance.execute();
        if (Objects.nonNull(result.get("error"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/all")
    public  ResponseEntity<?> getAllAttendances() {
        HashMap<String, Object> result = retrieveAllAttendance.execute();
        if (Objects.nonNull(result.get("error"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
