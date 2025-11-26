package com.iron_fit.iron_fit_backend.features.physical_evaluations.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.GetTrainerFromSession;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.requests.RegisterPhysicalEvaluationDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.dto.responses.PhysicalEvaluationResponseDto;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities.PhysicalEvaluation;
import com.iron_fit.iron_fit_backend.features.physical_evaluations.infrastructure.repository.PhysicalEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterPhysicalEvaluation {

    @Autowired
    private PhysicalEvaluationRepository evaluationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GetTrainerFromSession getTrainerFromSession;

    public HashMap<String, Object> execute(RegisterPhysicalEvaluationDto dto) {
        try {
            // Get authenticated trainer ID from session
            Long trainerId = getTrainerFromSession.execute();
            if (trainerId == null) {
                throw new RuntimeException("Authenticated user is not associated with a trainer");
            }

            // Validate client exists
            ClientEntity client = clientRepository.findById(dto.clientId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            // Validate trainer exists
            TrainerEntity trainer = trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));

            // Create evaluation
            PhysicalEvaluation evaluation = PhysicalEvaluation.builder()
                    .client(client)
                    .trainer(trainer)
                    .evaluationDate(dto.evaluationDate())
                    .weight(dto.weight())
                    .bmi(dto.bmi())
                    .bodyFatPercentage(dto.bodyFatPercentage())
                    .waistMeasurement(dto.waistMeasurement())
                    .hipMeasurement(dto.hipMeasurement())
                    .heightMeasurement(dto.heightMeasurement())
                    .notes(dto.notes())
                    .build();

            PhysicalEvaluation saved = evaluationRepository.save(evaluation);

            PhysicalEvaluationResponseDto response = new PhysicalEvaluationResponseDto(
                    saved.getId(),
                    saved.getClient().getId(),
                    saved.getClient().getFirstName() + " " + saved.getClient().getLastName(),
                    Long.valueOf(saved.getTrainer().getId()),
                    saved.getTrainer().getFirstName() + " " + saved.getTrainer().getLastName(),
                    saved.getEvaluationDate(),
                    saved.getWeight(),
                    saved.getBmi(),
                    saved.getBodyFatPercentage(),
                    saved.getWaistMeasurement(),
                    saved.getHipMeasurement(),
                    saved.getHeightMeasurement(),
                    saved.getNotes());

            return new HashMap<>() {
                {
                    put("message", "Physical evaluation created successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error creating physical evaluation: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
