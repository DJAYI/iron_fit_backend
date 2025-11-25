package com.iron_fit.iron_fit_backend.features.attendance.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response.AttendanceResponseDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetrieveClientAttendance {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute () {
        Long authenticatedClientId = getClientFromSession.execute();

        try {
            List<AttendanceEntity> clientAttendance = attendanceRepository.findAllByClient_Id(authenticatedClientId);
            List<AttendanceResponseDto> mappedClientAttendance = clientAttendance.stream().map(AttendanceResponseDto::fromEntity).toList();

            return new HashMap<String, Object>(){{
                put("attendances", mappedClientAttendance);
                put("message", "Client attendances successfully retrieved");
            }};
        } catch (Exception e) {
            return new  HashMap<String, Object>(){{
                put("message", "Client attendances could not be retrieved");
                put("error", true);
            }};
        }
    }
}
