package com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests.ModifyPhysicalEvaluationDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.responses.PhysicalEvaluationResponseDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository.PhysicalEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ModifyPhysicalEvaluation {

    @Autowired
    private PhysicalEvaluationRepository evaluationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public HashMap<String, Object> execute(Long id, ModifyPhysicalEvaluationDto dto) {
        try {
            // Validate evaluation exists
            PhysicalEvaluation evaluation = evaluationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Physical evaluation not found"));

            // Update client if provided
            if (dto.clientId() != null) {
                ClientEntity client = clientRepository.findById(dto.clientId())
                        .orElseThrow(() -> new RuntimeException("Client not found"));
                evaluation.setClient(client);
            }

            // Update trainer if provided
            if (dto.trainerId() != null) {
                TrainerEntity trainer = trainerRepository.findById(Long.valueOf(dto.trainerId()))
                        .orElseThrow(() -> new RuntimeException("Trainer not found"));
                evaluation.setTrainer(trainer);
            }

            // Update other fields if provided
            if (dto.evaluationDate() != null) {
                evaluation.setEvaluationDate(dto.evaluationDate());
            }
            if (dto.weight() != null) {
                evaluation.setWeight(dto.weight());
            }
            if (dto.bmi() != null) {
                evaluation.setBmi(dto.bmi());
            }
            if (dto.bodyFatPercentage() != null) {
                evaluation.setBodyFatPercentage(dto.bodyFatPercentage());
            }
            if (dto.waistMeasurement() != null) {
                evaluation.setWaistMeasurement(dto.waistMeasurement());
            }
            if (dto.hipMeasurement() != null) {
                evaluation.setHipMeasurement(dto.hipMeasurement());
            }
            if (dto.heightMeasurement() != null) {
                evaluation.setHeightMeasurement(dto.heightMeasurement());
            }
            if (dto.notes() != null) {
                evaluation.setNotes(dto.notes());
            }

            PhysicalEvaluation updated = evaluationRepository.save(evaluation);

            PhysicalEvaluationResponseDto response = new PhysicalEvaluationResponseDto(
                    updated.getId(),
                    updated.getClient().getId(),
                    updated.getClient().getFirstName() + " " + updated.getClient().getLastName(),
                    Long.valueOf(updated.getTrainer().getId()),
                    updated.getTrainer().getFirstName() + " " + updated.getTrainer().getLastName(),
                    updated.getEvaluationDate(),
                    updated.getWeight(),
                    updated.getBmi(),
                    updated.getBodyFatPercentage(),
                    updated.getWaistMeasurement(),
                    updated.getHipMeasurement(),
                    updated.getHeightMeasurement(),
                    updated.getNotes());

            return new HashMap<>() {
                {
                    put("message", "Physical evaluation updated successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error updating physical evaluation: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
