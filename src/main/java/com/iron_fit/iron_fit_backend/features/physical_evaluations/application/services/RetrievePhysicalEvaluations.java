package com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services;

import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.responses.PhysicalEvaluationResponseDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository.PhysicalEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrievePhysicalEvaluations {

    @Autowired
    private PhysicalEvaluationRepository evaluationRepository;

    public HashMap<String, Object> execute() {
        try {
            List<PhysicalEvaluation> evaluations = evaluationRepository.findAll();

            List<PhysicalEvaluationResponseDto> responses = evaluations.stream()
                    .map(this::mapToResponseDto)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Physical evaluations retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving physical evaluations: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeById(Long id) {
        try {
            PhysicalEvaluation evaluation = evaluationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Physical evaluation not found"));

            PhysicalEvaluationResponseDto response = mapToResponseDto(evaluation);

            return new HashMap<>() {
                {
                    put("message", "Physical evaluation retrieved successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving physical evaluation: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByClientId(Long clientId) {
        try {
            List<PhysicalEvaluation> evaluations = evaluationRepository
                    .findByClientIdOrderByEvaluationDateDesc(clientId);

            List<PhysicalEvaluationResponseDto> responses = evaluations.stream()
                    .map(this::mapToResponseDto)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Physical evaluations retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving physical evaluations: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByTrainerId(Long trainerId) {
        try {
            List<PhysicalEvaluation> evaluations = evaluationRepository.findByTrainerId(trainerId);

            List<PhysicalEvaluationResponseDto> responses = evaluations.stream()
                    .map(this::mapToResponseDto)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Physical evaluations retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving physical evaluations: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private PhysicalEvaluationResponseDto mapToResponseDto(PhysicalEvaluation evaluation) {
        return new PhysicalEvaluationResponseDto(
                evaluation.getId(),
                evaluation.getClient().getId(),
                evaluation.getClient().getFirstName() + " " + evaluation.getClient().getLastName(),
                Long.valueOf(evaluation.getTrainer().getId()),
                evaluation.getTrainer().getFirstName() + " " + evaluation.getTrainer().getLastName(),
                evaluation.getEvaluationDate(),
                evaluation.getWeight(),
                evaluation.getBmi(),
                evaluation.getBodyFatPercentage(),
                evaluation.getWaistMeasurement(),
                evaluation.getHipMeasurement(),
                evaluation.getHeightMeasurement(),
                evaluation.getNotes());
    }
}
