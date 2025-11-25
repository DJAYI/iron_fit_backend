package com.iron_fit.iron_fit_backend.features.reports.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("getAttendanceReportService")
public class GetAttendanceReport {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(String startDateStr, String endDateStr,
            Long clientId, Long trainerId, String statusStr) {
        try {
            List<AttendanceEntity> attendances = attendanceRepository.findAll();

            // Filter by date range
            if (startDateStr != null && !startDateStr.isEmpty()) {
                LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_DATE);
                attendances = attendances.stream()
                        .filter(a -> !a.getCreatedAt().toLocalDate().isBefore(startDate))
                        .collect(Collectors.toList());
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_DATE);
                attendances = attendances.stream()
                        .filter(a -> !a.getCreatedAt().toLocalDate().isAfter(endDate))
                        .collect(Collectors.toList());
            }

            // Filter by client
            if (clientId != null) {
                attendances = attendances.stream()
                        .filter(a -> a.getClient().getId().equals(clientId))
                        .collect(Collectors.toList());
            }

            // Filter by trainer (requires getting client's plans)
            if (trainerId != null) {
                List<TrainmentPlan> trainerPlans = trainmentPlanRepository.findByTrainerId(trainerId);
                List<Long> trainerClientIds = trainerPlans.stream()
                        .map(p -> p.getClient().getId())
                        .distinct()
                        .collect(Collectors.toList());

                attendances = attendances.stream()
                        .filter(a -> trainerClientIds.contains(a.getClient().getId()))
                        .collect(Collectors.toList());
            }

            // Filter by status
            if (statusStr != null && !statusStr.isEmpty()) {
                AttendanceStatus status = AttendanceStatus.valueOf(statusStr.toUpperCase());
                attendances = attendances.stream()
                        .filter(a -> a.getStatus() == status)
                        .collect(Collectors.toList());
            }

            // Build response DTOs
            List<Map<String, Object>> attendanceData = attendances.stream()
                    .map(a -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", a.getId());
                        map.put("clientId", a.getClient().getId());
                        map.put("clientName", a.getClient().getFirstName() + " " + a.getClient().getLastName());
                        map.put("date", a.getCreatedAt().toLocalDate());
                        map.put("time", a.getCreatedAt().toLocalTime());
                        map.put("status", a.getStatus());
                        map.put("completed", a.getCompleted());
                        map.put("observations", a.getObservations());
                        return map;
                    })
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Attendance report generated successfully");
                    put("data", attendanceData);
                    put("count", attendanceData.size());
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error generating attendance report: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
