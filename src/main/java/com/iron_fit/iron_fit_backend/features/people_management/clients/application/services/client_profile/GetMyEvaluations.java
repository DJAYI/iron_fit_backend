package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.responses.PhysicalEvaluationResponseDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository.PhysicalEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("clientGetMyEvaluations")
public class GetMyEvaluations {

    @Autowired
    private PhysicalEvaluationRepository evaluationRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute() {
        try {
            Long authenticatedClientId = getClientFromSession.execute();

            if (authenticatedClientId == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found in session");
                        put("error", true);
                    }
                };
            }

            List<PhysicalEvaluation> evaluations = evaluationRepository
                    .findByClientIdOrderByEvaluationDateDesc(authenticatedClientId);

            List<PhysicalEvaluationResponseDto> responses = evaluations.stream()
                    .map(eval -> new PhysicalEvaluationResponseDto(
                            eval.getId(),
                            eval.getClient().getId(),
                            eval.getClient().getFirstName() + " " + eval.getClient().getLastName(),
                            Long.valueOf(eval.getTrainer().getId()),
                            eval.getTrainer().getFirstName() + " " + eval.getTrainer().getLastName(),
                            eval.getEvaluationDate(),
                            eval.getWeight(),
                            eval.getBmi(),
                            eval.getBodyFatPercentage(),
                            eval.getWaistMeasurement(),
                            eval.getHipMeasurement(),
                            eval.getHeightMeasurement(),
                            eval.getNotes()))
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Evaluations retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving evaluations: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
