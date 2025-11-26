package com.iron_fit.iron_fit_backend.features.attendance.domain.dto.requests;

import jakarta.validation.constraints.NotNull;

public record MarkAttendanceCompletedDto(
        @NotNull(message = "Attendance ID is required") Long attendanceId,

        @NotNull(message = "Completed status is required") Boolean completed) {
}
