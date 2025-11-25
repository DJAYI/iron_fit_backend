package com.iron_fit.iron_fit_backend.features.attendance.attendance_exercise.domain.entities;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities.RoutineExercise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance_exercises")
public class AttendanceExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private AttendanceEntity attendance;

    @ManyToOne
    @JoinColumn(name = "routine_exercise_id", nullable = false)
    private RoutineExercise routineExercise;

    @Column(nullable = false)
    private Boolean completed;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
