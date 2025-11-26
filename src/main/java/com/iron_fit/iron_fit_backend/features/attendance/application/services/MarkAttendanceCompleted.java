package com.iron_fit.iron_fit_backend.features.attendance.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.requests.MarkAttendanceCompletedDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response.AttendanceResponseDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class MarkAttendanceCompleted {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute(MarkAttendanceCompletedDto dto) {
        Optional<AttendanceEntity> attendanceOptional = attendanceRepository.findById(dto.attendanceId());

        if (attendanceOptional.isEmpty()) {
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Attendance not found with ID: " + dto.attendanceId());
                }
            };
        }

        try {
            AttendanceEntity attendance = attendanceOptional.get();
            attendance.setCompleted(dto.completed());
            attendanceRepository.save(attendance);

            return new HashMap<String, Object>() {
                {
                    put("message", "Attendance completion status updated successfully");
                    put("attendance", AttendanceResponseDto.fromEntity(attendance));
                }
            };
        } catch (Exception e) {
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Error updating attendance completion status: " + e.getMessage());
                }
            };
        }
    }
}
