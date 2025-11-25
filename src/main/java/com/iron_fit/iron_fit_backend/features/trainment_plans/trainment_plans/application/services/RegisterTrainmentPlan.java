package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests.RegisterTrainmentPlanDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses.TrainmentPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RegisterTrainmentPlan {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainmentPlanObjectiveRepository objectiveRepository;

    @Autowired
    private TrainmentPlanStateRepository stateRepository;

    public HashMap<String, Object> execute(RegisterTrainmentPlanDto dto) {
        try {
            // Validate client exists
            Optional<ClientEntity> clientOpt = clientRepository.findById(dto.clientId());
            if (clientOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found with ID: " + dto.clientId());
                        put("error", true);
                    }
                };
            }

            // Validate trainer exists
            Optional<TrainerEntity> trainerOpt = trainerRepository.findById(dto.trainerId());
            if (trainerOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Trainer not found with ID: " + dto.trainerId());
                        put("error", true);
                    }
                };
            }

            // Validate objective exists
            Optional<TrainmentPlanObjective> objectiveOpt = objectiveRepository.findById(dto.objectiveId());
            if (objectiveOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Objective not found with ID: " + dto.objectiveId());
                        put("error", true);
                    }
                };
            }

            // Validate state exists
            Optional<TrainmentPlanState> stateOpt = stateRepository.findById(dto.stateId());
            if (stateOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "State not found with ID: " + dto.stateId());
                        put("error", true);
                    }
                };
            }

            // Validate dates
            if (dto.endDate() != null && dto.endDate().isBefore(dto.startDate())) {
                return new HashMap<>() {
                    {
                        put("message", "End date cannot be before start date");
                        put("error", true);
                    }
                };
            }

            // Create training plan entity
            TrainmentPlan trainmentPlan = TrainmentPlan.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .startDate(dto.startDate())
                    .endDate(dto.endDate())
                    .client(clientOpt.get())
                    .trainer(trainerOpt.get())
                    .objective(objectiveOpt.get())
                    .state(stateOpt.get())
                    .build();

            // Save training plan
            TrainmentPlan savedPlan = trainmentPlanRepository.save(trainmentPlan);

            // Create response DTO
            TrainmentPlanResponseDto responseDto = new TrainmentPlanResponseDto(
                    savedPlan.getId(),
                    savedPlan.getName(),
                    savedPlan.getDescription(),
                    savedPlan.getStartDate(),
                    savedPlan.getEndDate(),
                    savedPlan.getClient().getId(),
                    savedPlan.getClient().getFirstName() + " " + savedPlan.getClient().getLastName(),
                    Long.valueOf(savedPlan.getTrainer().getId()),
                    savedPlan.getTrainer().getFirstName() + " " + savedPlan.getTrainer().getLastName(),
                    savedPlan.getObjective().getId(),
                    savedPlan.getObjective().getName(),
                    savedPlan.getState().getId(),
                    savedPlan.getState().getName());

            return new HashMap<>() {
                {
                    put("message", "Training plan registered successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error registering training plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
