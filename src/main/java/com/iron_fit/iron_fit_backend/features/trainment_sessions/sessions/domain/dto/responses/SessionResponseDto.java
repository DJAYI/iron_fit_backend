package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.responses;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionResponseDto(
        Long id,
        LocalDate startDate,
        LocalTime startTime,
        String notes,
        Long routineId,
        String routineName) {
}
