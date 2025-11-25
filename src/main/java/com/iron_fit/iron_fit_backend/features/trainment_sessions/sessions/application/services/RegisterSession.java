package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests.RegisterSessionDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.responses.SessionResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RegisterSession {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoutineRepository routineRepository;

    public HashMap<String, Object> execute(RegisterSessionDto dto) {
        try {
            // Validate routine exists
            Optional<Routine> routineOpt = routineRepository.findById(dto.routineId());
            if (routineOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Routine not found with ID: " + dto.routineId());
                        put("error", true);
                    }
                };
            }

            // Create session entity
            Session session = Session.builder()
                    .startDate(dto.startDate())
                    .startTime(dto.startTime())
                    .notes(dto.notes())
                    .routine(routineOpt.get())
                    .build();

            // Save session
            Session savedSession = sessionRepository.save(session);

            // Create response DTO
            SessionResponseDto responseDto = new SessionResponseDto(
                    savedSession.getId(),
                    savedSession.getStartDate(),
                    savedSession.getStartTime(),
                    savedSession.getNotes(),
                    savedSession.getRoutine().getId(),
                    savedSession.getRoutine().getName());

            return new HashMap<>() {
                {
                    put("message", "Session registered successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error registering session: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
