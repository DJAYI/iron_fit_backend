package com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;

import java.time.LocalDateTime;

public record AttendanceResponseDto(
        Long id,
        Long clientId,
        LocalDateTime dateTime
) {
    public static AttendanceResponseDto fromEntity(AttendanceEntity entity) {
        return new AttendanceResponseDto(
                entity.getId(),
                entity.getClient().getId(),
                entity.getCreatedAt()
        );
    }
}
