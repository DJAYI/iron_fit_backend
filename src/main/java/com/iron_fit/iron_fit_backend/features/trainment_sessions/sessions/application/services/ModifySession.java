package com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.application.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.domain.entities.Routine;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.routines.infrastructure.repository.RoutineRepository;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.requests.ModifySessionDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.dto.responses.SessionResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.infrastructure.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class ModifySession {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoutineRepository routineRepository;

    public HashMap<String, Object> execute(ModifySessionDto dto) {
        try {
            // Validate session exists
            Optional<Session> sessionOpt = sessionRepository.findById(dto.id());
            if (sessionOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Session not found with ID: " + dto.id());
                        put("error", true);
                    }
                };
            }

            Session session = sessionOpt.get();

            // Update start date if provided
            if (dto.startDate() != null) {
                session.setStartDate(dto.startDate());
            }

            // Update start time if provided
            if (dto.startTime() != null) {
                session.setStartTime(dto.startTime());
            }

            // Update notes if provided
            if (dto.notes() != null) {
                session.setNotes(dto.notes());
            }

            // Update routine if provided
            if (dto.routineId() != null) {
                Optional<Routine> routineOpt = routineRepository.findById(dto.routineId());
                if (routineOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Routine not found with ID: " + dto.routineId());
                            put("error", true);
                        }
                    };
                }
                session.setRoutine(routineOpt.get());
            }

            // Save updated session
            Session updatedSession = sessionRepository.save(session);

            // Create response DTO
            SessionResponseDto responseDto = new SessionResponseDto(
                    updatedSession.getId(),
                    updatedSession.getStartDate(),
                    updatedSession.getStartTime(),
                    updatedSession.getNotes(),
                    updatedSession.getRoutine().getId(),
                    updatedSession.getRoutine().getName());

            return new HashMap<>() {
                {
                    put("message", "Session modified successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error modifying session: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
