package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.application.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.dto.responses.TrainmentPlanResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetrieveTrainmentPlans {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    public HashMap<String, Object> execute() {
        try {
            List<TrainmentPlan> plans = trainmentPlanRepository.findAll();

            List<TrainmentPlanResponseDto> responseDtos = plans.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Training plans retrieved successfully");
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving training plans: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> execute(Long id) {
        try {
            Optional<TrainmentPlan> planOpt = trainmentPlanRepository.findById(id);

            if (planOpt.isEmpty()) {
                return new HashMap<>() {
                    {
                        put("message", "Training plan not found with ID: " + id);
                        put("error", true);
                    }
                };
            }

            TrainmentPlanResponseDto responseDto = mapToResponseDto(planOpt.get());

            return new HashMap<>() {
                {
                    put("message", "Training plan retrieved successfully");
                    put("data", responseDto);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving training plan: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByClientId(Long clientId) {
        try {
            List<TrainmentPlan> plans = trainmentPlanRepository.findByClientId(clientId);

            List<TrainmentPlanResponseDto> responseDtos = plans.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Training plans retrieved successfully for client ID: " + clientId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving training plans by client: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    public HashMap<String, Object> executeByTrainerId(Long trainerId) {
        try {
            List<TrainmentPlan> plans = trainmentPlanRepository.findByTrainerId(trainerId);

            List<TrainmentPlanResponseDto> responseDtos = plans.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            return new HashMap<>() {
                {
                    put("message", "Training plans retrieved successfully for trainer ID: " + trainerId);
                    put("data", responseDtos);
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving training plans by trainer: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }

    private TrainmentPlanResponseDto mapToResponseDto(TrainmentPlan plan) {
        return new TrainmentPlanResponseDto(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getStartDate(),
                plan.getEndDate(),
                plan.getClient().getId(),
                plan.getClient().getFirstName() + " " + plan.getClient().getLastName(),
                Long.valueOf(plan.getTrainer().getId()),
                plan.getTrainer().getFirstName() + " " + plan.getTrainer().getLastName(),
                plan.getObjective().getId(),
                plan.getObjective().getName(),
                plan.getState().getId(),
                plan.getState().getName());
    }
}
