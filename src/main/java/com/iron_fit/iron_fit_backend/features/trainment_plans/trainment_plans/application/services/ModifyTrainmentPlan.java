package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.requests.ModifyTrainmentPlanDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses.TrainmentPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class ModifyTrainmentPlan {

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

    public HashMap<String, Object> execute(ModifyTrainmentPlanDto dto) {
        try {
            // Validate training plan exists
            Optional<TrainmentPlan> planOpt = trainmentPlanRepository.findById(dto.id());
            if (planOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Training plan not found with ID: " + dto.id());
                        put("error", true);
                    }
                };
            }

            TrainmentPlan plan = planOpt.get();

            // Update name if provided
            if (dto.name() != null && !dto.name().isBlank()) {
                plan.setName(dto.name());
            }

            // Update description if provided
            if (dto.description() != null) {
                plan.setDescription(dto.description());
            }

            // Update start date if provided
            if (dto.startDate() != null) {
                plan.setStartDate(dto.startDate());
            }

            // Update end date if provided
            if (dto.endDate() != null) {
                // Validate dates
                if (dto.endDate().isBefore(plan.getStartDate())) {
                    return new HashMap<>() {
                        {
                            put("message", "End date cannot be before start date");
                            put("error", true);
                        }
                    };
                }
                plan.setEndDate(dto.endDate());
            }

            // Update client if provided
            if (dto.clientId() != null) {
                Optional<ClientEntity> clientOpt = clientRepository.findById(dto.clientId());
                if (clientOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Client not found with ID: " + dto.clientId());
                            put("error", true);
                        }
                    };
                }
                plan.setClient(clientOpt.get());
            }

            // Update trainer if provided
            if (dto.trainerId() != null) {
                Optional<TrainerEntity> trainerOpt = trainerRepository.findById(dto.trainerId());
                if (trainerOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Trainer not found with ID: " + dto.trainerId());
                            put("error", true);
                        }
                    };
                }
                plan.setTrainer(trainerOpt.get());
            }

            // Update objective if provided
            if (dto.objectiveId() != null) {
                Optional<TrainmentPlanObjective> objectiveOpt = objectiveRepository.findById(dto.objectiveId());
                if (objectiveOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "Objective not found with ID: " + dto.objectiveId());
                            put("error", true);
                        }
                    };
                }
                plan.setObjective(objectiveOpt.get());
            }

            // Update state if provided
            if (dto.stateId() != null) {
                Optional<TrainmentPlanState> stateOpt = stateRepository.findById(dto.stateId());
                if (stateOpt.isEmpty()) {
                    return new HashMap<>() {
                        {
                            put("message", "State not found with ID: " + dto.stateId());
                            put("error", true);
                        }
                    };
                }
                plan.setState(stateOpt.get());
            }

            // Save updated training plan
            TrainmentPlan updatedPlan = trainmentPlanRepository.save(plan);

            // Create response DTO
            TrainmentPlanResponseDto responseDto = new TrainmentPlanResponseDto(
                    updatedPlan.getId(),
                    updatedPlan.getName(),
                    updatedPlan.getDescription(),
                    updatedPlan.getStartDate(),
                    updatedPlan.getEndDate(),
                    updatedPlan.getClient().getId(),
                    updatedPlan.getClient().getFirstName() + " " + updatedPlan.getClient().getLastName(),
                    Long.valueOf(updatedPlan.getTrainer().getId()),
                    updatedPlan.getTrainer().getFirstName() + " " + updatedPlan.getTrainer().getLastName(),
                    updatedPlan.getObjective().getId(),
                    updatedPlan.getObjective().getName(),
                    updatedPlan.getState().getId(),
                    updatedPlan.getState().getName());

            return new HashMap<>() {
                {
                    put("message", "Training plan modified successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error modifying training plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
