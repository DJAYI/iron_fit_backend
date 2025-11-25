package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.trainer_scoped;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.GetTrainerFromSession;
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

@Service("trainerGetClientCompliance")
public class GetClientCompliance {

    @Autowired
    private GetTrainerFromSession getTrainerFromSession;

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

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute(Long clientId) {
        try {
            Long trainerId = getTrainerFromSession.execute();
            if (trainerId == null) {
                return new HashMap<>() {
                    {
                        put("message", "No trainer found in session");
                        put("error", true);
                    }
                };
            }

            // Verify client exists
            ClientEntity client = clientRepository.findById(clientId).orElse(null);
            if (client == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found");
                        put("error", true);
                    }
                };
            }

            // Get all plans for this client
            List<TrainmentPlan> clientPlans = trainmentPlanRepository.findByClientId(clientId);

            // Verify this trainer has a plan with this client
            boolean hasRelationship = clientPlans.stream()
                    .anyMatch(p -> (long) p.getTrainer().getId() == trainerId);

            if (!hasRelationship) {
                return new HashMap<>() {
                    {
                        put("message", "This client is not assigned to you");
                        put("error", true);
                    }
                };
            }

            // Get active plan for date-based stats
            LocalDate today = LocalDate.now();
            TrainmentPlan activePlan = clientPlans.stream()
                    .filter(p -> !today.isBefore(p.getStartDate()) && !today.isAfter(p.getEndDate()))
                    .findFirst()
                    .orElse(null);

            // Calculate attendance statistics
            List<AttendanceEntity> allAttendances = attendanceRepository.findAllByClient_Id(clientId);
            long totalAttendances = allAttendances.size();
            long attendedCount = allAttendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.ATTENDED ||
                            a.getStatus() == AttendanceStatus.ATTENDED_NO_ROUTINE)
                    .count();
            long notAttendedCount = allAttendances.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.NOT_ATTENDED)
                    .count();
            long completedSessionsCount = allAttendances.stream()
                    .filter(a -> a.getCompleted() != null && a.getCompleted())
                    .count();

            double attendancePercentage = totalAttendances > 0
                    ? (double) attendedCount / totalAttendances * 100
                    : 0.0;

            // Calculate session statistics for active plan
            Long programmedSessions = 0L;
            if (activePlan != null) {
                // Get all routines from active plan
                List<Routine> routines = routineRepository.findByTrainmentPlanId(activePlan.getId());
                List<Session> sessions = routines.stream()
                        .flatMap(routine -> sessionRepository.findByRoutineId(routine.getId()).stream())
                        .toList();
                programmedSessions = (long) sessions.size();
            }

            HashMap<String, Object> complianceData = new HashMap<>();
            complianceData.put("clientId", clientId);
            complianceData.put("clientName", client.getFirstName() + " " + client.getLastName());
            complianceData.put("totalAttendances", totalAttendances);
            complianceData.put("attendedCount", attendedCount);
            complianceData.put("notAttendedCount", notAttendedCount);
            complianceData.put("attendancePercentage", Math.round(attendancePercentage * 100.0) / 100.0);
            complianceData.put("programmedSessions", programmedSessions);
            complianceData.put("completedSessions", completedSessionsCount);
            complianceData.put("hasActivePlan", activePlan != null);

            if (activePlan != null) {
                complianceData.put("activePlanId", activePlan.getId());
                complianceData.put("activePlanName", activePlan.getName());
            }

            return new HashMap<>() {
                {
                    put("message", "Client compliance retrieved successfully");
                    put("data", complianceData);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving client compliance: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
