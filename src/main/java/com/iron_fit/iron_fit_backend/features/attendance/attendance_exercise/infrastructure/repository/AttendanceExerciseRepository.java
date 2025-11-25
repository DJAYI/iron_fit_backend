package com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.domain.entities.AttendanceExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceExerciseRepository extends JpaRepository<AttendanceExercise, Long> {
    List<AttendanceExercise> findByAttendanceId(Long attendanceId);

    List<AttendanceExercise> findByRoutineExerciseId(Long routineExerciseId);
}
