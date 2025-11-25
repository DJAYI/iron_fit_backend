package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegisterSessionDto(
        @NotNull(message = "Start date is required") LocalDate startDate,

        @NotNull(message = "Start time is required") LocalTime startTime,

        String notes,

        @NotNull(message = "Routine ID is required") Long routineId) {
}
