package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByRoutineId(Long routineId);

    List<Session> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Session> findByRoutineIdAndStartDateBetween(Long routineId, LocalDate startDate, LocalDate endDate);
}
