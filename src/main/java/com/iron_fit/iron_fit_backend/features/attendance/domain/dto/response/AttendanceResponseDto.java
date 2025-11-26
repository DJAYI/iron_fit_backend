package com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceResponseDto(
        Long id,
        Long clientId,
        LocalDateTime dateTime,
        AttendanceStatus status,
        Boolean completed,
        String observations,
        Long sessionId) {
    public static AttendanceResponseDto fromEntity(AttendanceEntity entity) {
        return new AttendanceResponseDto(
                entity.getId(),
                entity.getClient().getId(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getCompleted(),
                entity.getObservations(),
                entity.getSession() != null ? entity.getSession().getId() : null);
    }
}
