package com.iron_fit.iron_fit_backend.features.reports.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.domain.entities.AttendanceExercise;
import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.infrastructure.repository.AttendanceExerciseRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository.RoutineExerciseRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service("getExerciseComplianceReportService")
public class GetExerciseComplianceReport {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private AttendanceExerciseRepository attendanceExerciseRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(Long planId) {
        try {
            TrainmentPlan plan = trainmentPlanRepository.findById(planId).orElse(null);

            if (plan == null) {
                return new HashMap<>() {
                    {
                        put("message", "Training plan not found");
                        put("error", true);
                    }
                };
            }

            // Get all routines for the plan
            List<Routine> routines = routineRepository.findByTrainmentPlanId(planId);

            // Get all routine exercises
            List<RoutineExercise> routineExercises = routines.stream()
                    .flatMap(routine -> routineExerciseRepository.findByRoutineIdOrderByOrderAsc(routine.getId())
                            .stream())
                    .collect(Collectors.toList());

            long totalExercises = routineExercises.size();

            // Get all completed exercises via AttendanceExercise
            List<Long> routineExerciseIds = routineExercises.stream()
                    .map(RoutineExercise::getId)
                    .collect(Collectors.toList());

            long completedExercises = routineExerciseIds.stream()
                    .flatMap(id -> attendanceExerciseRepository.findByRoutineExerciseId(id).stream())
                    .filter(ae -> ae.getCompleted() != null && ae.getCompleted())
                    .count();

            double completionPercentage = totalExercises > 0
                    ? (double) completedExercises / totalExercises * 100
                    : 0.0;

            HashMap<String, Object> complianceData = new HashMap<>();
            complianceData.put("planId", planId);
            complianceData.put("planName", plan.getName());
            complianceData.put("clientId", plan.getClient().getId());
            complianceData.put("clientName", plan.getClient().getFirstName() + " " + plan.getClient().getLastName());
            complianceData.put("totalExercises", totalExercises);
            complianceData.put("completedExercises", completedExercises);
            complianceData.put("completionPercentage", Math.round(completionPercentage * 100.0) / 100.0);
            complianceData.put("routineCount", routines.size());

            return new HashMap<>() {
                {
                    put("message", "Exercise compliance report generated successfully");
                    put("data", complianceData);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error generating exercise compliance report: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
