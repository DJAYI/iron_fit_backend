package com.iron_fit.iron_fit_backend.features.attendance.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.domain.entities.AttendanceExercise;
import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.infrastructure.repository.AttendanceExerciseRepository;
import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.requests.MarkAttendanceCompletedDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response.AttendanceResponseDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.infrastructure.repository.RoutineExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MarkAttendanceCompleted {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private AttendanceExerciseRepository attendanceExerciseRepository;

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute(MarkAttendanceCompletedDto dto) {
        Optional<AttendanceEntity> attendanceOptional = attendanceRepository.findById(dto.attendanceId());

        if (attendanceOptional.isEmpty()) {
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Attendance not found with ID: " + dto.attendanceId());
                }
            };
        }

        try {
            AttendanceEntity attendance = attendanceOptional.get();
            attendance.setCompleted(dto.completed());
            attendanceRepository.save(attendance);

            // If marking as completed and there's a session, create AttendanceExercise
            // records
            if (dto.completed() && attendance.getSession() != null) {
                Long routineId = attendance.getSession().getRoutine().getId();
                List<RoutineExercise> routineExercises = routineExerciseRepository
                        .findByRoutineIdOrderByOrderAsc(routineId);

                // Create AttendanceExercise for each exercise in the routine
                for (RoutineExercise routineExercise : routineExercises) {
                    // Check if already exists to avoid duplicates
                    List<AttendanceExercise> existing = attendanceExerciseRepository
                            .findByAttendanceIdAndRoutineExerciseId(attendance.getId(), routineExercise.getId());

                    if (existing.isEmpty()) {
                        AttendanceExercise attendanceExercise = AttendanceExercise.builder()
                                .attendance(attendance)
                                .routineExercise(routineExercise)
                                .completed(true)
                                .notes("Auto-completed")
                                .build();
                        attendanceExerciseRepository.save(attendanceExercise);
                    }
                }
            }

            return new HashMap<String, Object>() {
                {
                    put("message", "Attendance completion status updated successfully");
                    put("attendance", AttendanceResponseDto.fromEntity(attendance));
                }
            };
        } catch (Exception e) {
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Error updating attendance completion status: " + e.getMessage());
                }
            };
        }
    }
}
