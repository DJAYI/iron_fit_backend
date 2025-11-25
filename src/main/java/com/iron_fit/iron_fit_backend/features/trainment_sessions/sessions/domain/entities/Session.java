package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sessions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;
}
