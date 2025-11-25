package com.iron_fit.iron_fit_backend.features.dashboard.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service("getDashboardStatsService")
public class GetDashboardStats {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute() {
        try {
            // Total clients count
            long totalClients = clientRepository.count();

            // Total trainers count
            long totalTrainers = trainerRepository.count();

            // Active plans (today is between start and end dates)
            LocalDate today = LocalDate.now();
            List<TrainmentPlan> allPlans = trainmentPlanRepository.findAll();
            long activePlans = allPlans.stream()
                    .filter(p -> !today.isBefore(p.getStartDate()) &&
                            (p.getEndDate() == null || !today.isAfter(p.getEndDate())))
                    .count();

            // Weekly attendance (last 7 days)
            LocalDate sevenDaysAgo = today.minusDays(7);
            List<AttendanceEntity> allAttendances = attendanceRepository.findAll();
            long weeklyAttendance = allAttendances.stream()
                    .filter(a -> {
                        LocalDate attendanceDate = a.getCreatedAt().toLocalDate();
                        return !attendanceDate.isBefore(sevenDaysAgo) && !attendanceDate.isAfter(today);
                    })
                    .filter(a -> a.getStatus() == AttendanceStatus.ATTENDED ||
                            a.getStatus() == AttendanceStatus.ATTENDED_NO_ROUTINE)
                    .count();

            HashMap<String, Object> stats = new HashMap<>();
            stats.put("totalClients", totalClients);
            stats.put("totalTrainers", totalTrainers);
            stats.put("activePlans", activePlans);
            stats.put("weeklyAttendance", weeklyAttendance);
            stats.put("calculatedAt", LocalDateTime.now());

            return new HashMap<>() {
                {
                    put("message", "Dashboard statistics retrieved successfully");
                    put("data", stats);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving dashboard statistics: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
