package com.iron_fit.iron_fit_backend.features.reports.infrastructure;

import com.iron_fit.iron_fit_backend.features.reports.application.services.GetAttendanceReport;
import com.iron_fit.iron_fit_backend.features.reports.application.services.GetClientComplianceReport;
import com.iron_fit.iron_fit_backend.features.reports.application.services.GetExerciseComplianceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/reports")
public class ReportsController {

    @Autowired
    private GetAttendanceReport getAttendanceReport;

    @Autowired
    private GetClientComplianceReport getClientComplianceReport;

    @Autowired
    private GetExerciseComplianceReport getExerciseComplianceReport;

    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendanceReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false) String status) {

        HashMap<String, Object> result = getAttendanceReport.execute(
                startDate, endDate, clientId, trainerId, status);

        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/compliance/client/{clientId}")
    public ResponseEntity<?> getClientCompliance(@PathVariable Long clientId) {
        HashMap<String, Object> result = getClientComplianceReport.execute(clientId);

        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/compliance/exercises")
    public ResponseEntity<?> getExerciseCompliance(@RequestParam Long planId) {
        HashMap<String, Object> result = getExerciseComplianceReport.execute(planId);

        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }
}
