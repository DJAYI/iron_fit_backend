package com.iron_fit.iron_fit_backend.features.routines_and_exercises.routine_exercises.domain.entities;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities.Exercise;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routine_exercises")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "exercise_order", nullable = false)
    private Integer order;

    @Column(nullable = false)
    private Integer sets;

    @Column(nullable = false)
    private Integer reps;

    @Column(name = "time_seconds")
    private Integer timeSeconds;

    @Column(name = "rest_seconds", nullable = false)
    private Integer restSeconds;

    @Column(name = "target_weight")
    private Double targetWeight;
}
