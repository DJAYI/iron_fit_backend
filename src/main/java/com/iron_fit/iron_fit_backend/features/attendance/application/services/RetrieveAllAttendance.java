package com.iron_fit.iron_fit_backend.features.attendance.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response.AttendanceResponseDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveAllAttendance {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute () {
        try {
            List<AttendanceEntity> attendanceEntities = attendanceRepository.findAll();
            List<AttendanceResponseDto> mappedAttendances = attendanceEntities.stream().map(AttendanceResponseDto::fromEntity).toList();

            return new HashMap<String, Object>() {{
                put("attendances", mappedAttendances);
                put("message", "Retrieved all attendances successfully");
            }};
        } catch (Exception e){
            return new HashMap<String, Object>() {{
                put("message", e.getMessage());
                put("error", true);
            }};
        }
    }
}
