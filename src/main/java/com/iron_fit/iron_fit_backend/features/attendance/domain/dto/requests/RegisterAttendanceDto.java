package com.iron_fit.iron_fit_backend.features.attendance.domain.dto.requests;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

public record RegisterAttendanceDto(
        @NotNull(message = "Status is required") AttendanceStatus status,

        String observations) {
}
