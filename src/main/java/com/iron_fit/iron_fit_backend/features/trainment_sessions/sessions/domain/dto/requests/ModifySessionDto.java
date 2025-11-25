package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ModifySessionDto(
        @NotNull(message = "Session ID is required") Long id,

        LocalDate startDate,

        LocalTime startTime,

        String notes,

        Long routineId) {
}
