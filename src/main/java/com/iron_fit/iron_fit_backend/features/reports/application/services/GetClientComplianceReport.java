package com.iron_fit.iron_fit_backend.features.reports.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service("getClientComplianceReportService")
public class GetClientComplianceReport {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(Long clientId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElse(null);

            if (client == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found");
                        put("error", true);
                    }
                };
            }

            // Get active plan
            LocalDate today = LocalDate.now();
            List<TrainmentPlan> clientPlans = trainmentPlanRepository.findByClientId(clientId);
            TrainmentPlan activePlan = clientPlans.stream()
                    .filter(p -> !today.isBefore(p.getStartDate()) &&
                            (p.getEndDate() == null || !today.isAfter(p.getEndDate())))
                    .findFirst()
                    .orElse(null);

            // Calculate attendance statistics
            List<AttendanceEntity> attendances = attendanceRepository.findAllByClient_Id(clientId);
            long totalAttendances = attendances.size();
            long completedSessions = attendances.stream()
                    .filter(a -> a.getCompleted() != null && a.getCompleted())
                    .count();

            // Calculate session statistics for active plan
            Long programmedSessions = 0L;
            if (activePlan != null) {
                List<Routine> routines = routineRepository.findByTrainmentPlanId(activePlan.getId());
                List<Session> sessions = routines.stream()
                        .flatMap(routine -> sessionRepository.findByRoutineId(routine.getId()).stream())
                        .toList();
                programmedSessions = (long) sessions.size();
            }

            double completionPercentage = programmedSessions > 0
                    ? (double) completedSessions / programmedSessions * 100
                    : 0.0;

            HashMap<String, Object> complianceData = new HashMap<>();
            complianceData.put("clientId", clientId);
            complianceData.put("clientName", client.getFirstName() + " " + client.getLastName());
            complianceData.put("totalAttendances", totalAttendances);
            complianceData.put("completedSessions", completedSessions);
            complianceData.put("programmedSessions", programmedSessions);
            complianceData.put("completionPercentage", Math.round(completionPercentage * 100.0) / 100.0);
            complianceData.put("hasActivePlan", activePlan != null);

            if (activePlan != null) {
                complianceData.put("activePlanId", activePlan.getId());
                complianceData.put("activePlanName", activePlan.getName());
            }

            return new HashMap<>() {
                {
                    put("message", "Client compliance report generated successfully");
                    put("data", complianceData);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error generating compliance report: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
