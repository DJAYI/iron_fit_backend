package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services;

import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.responses.SessionResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetrieveSessions {

    @Autowired
    private SessionRepository sessionRepository;

    public HashMap<String, Object> execute() {
        try {
            List<Session> sessions = sessionRepository.findAll();

            List<SessionResponseDto> responseDtos = sessions.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Sessions retrieved successfully");
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving sessions: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> execute(Long id) {
        try {
            Optional<Session> sessionOpt = sessionRepository.findById(id);

            if (sessionOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Session not found with ID: " + id);
                        put("error", true);
                    }
                };
            }

            SessionResponseDto responseDto = mapToResponseDto(sessionOpt.get());

            return new HashMap<>() {
                {
                    put("message", "Session retrieved successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving session: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByRoutineId(Long routineId) {
        try {
            List<Session> sessions = sessionRepository.findByRoutineId(routineId);

            List<SessionResponseDto> responseDtos = sessions.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Sessions retrieved successfully for routine ID: " + routineId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving sessions by routine: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<Session> sessions = sessionRepository.findByStartDateBetween(startDate, endDate);

            List<SessionResponseDto> responseDtos = sessions.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Sessions retrieved successfully for date range");
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving sessions by date range: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private SessionResponseDto mapToResponseDto(Session session) {
        return new SessionResponseDto(
                session.getId(),
                session.getStartDate(),
                session.getStartTime(),
                session.getNotes(),
                session.getRoutine().getId(),
                session.getRoutine().getName());
    }
}
