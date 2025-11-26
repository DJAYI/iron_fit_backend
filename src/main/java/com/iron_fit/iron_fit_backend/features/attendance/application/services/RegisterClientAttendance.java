package com.iron_fit.iron_fit_backend.features.attendance.application.services;

import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.requests.RegisterAttendanceDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.dto.response.AttendanceResponseDto;
import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository.AttendanceRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class RegisterClientAttendance {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute(RegisterAttendanceDto dto) {
        Long authenticatedClientId = getClientFromSession.execute();

        if (authenticatedClientId == null)
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Client ID could not be found");
                }
            };

        Optional<ClientEntity> clientOptional = clientRepository.findById(authenticatedClientId);

        if (clientOptional.isEmpty())
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Client was not found");
                }
            };

        try {
            AttendanceEntity attendanceEntity = new AttendanceEntity();
            attendanceEntity.setClient(clientOptional.get());
            attendanceEntity.setStatus(dto.status());
            attendanceEntity.setObservations(dto.observations());
            attendanceEntity.setCompleted(false);
            attendanceRepository.save(attendanceEntity);

            return new HashMap<String, Object>() {
                {
                    put("message", "Attendance was successfully registered");
                    put("attendance", AttendanceResponseDto.fromEntity(attendanceEntity));
                }
            };
        } catch (Exception e) {
            return new HashMap<String, Object>() {
                {
                    put("error", true);
                    put("message", "Error registering attendance");
                }
            };
        }
    }
}
