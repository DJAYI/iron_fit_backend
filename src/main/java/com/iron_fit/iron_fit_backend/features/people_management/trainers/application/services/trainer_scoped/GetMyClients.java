package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.trainer_scoped;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.GetTrainerFromSession;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.domain.entities.TrainmentPlan;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plans.infrastructure.repository.TrainmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service("trainerGetMyClients")
public class GetMyClients {

    @Autowired
    private TrainmentPlanRepository trainmentPlanRepository;

    @Autowired
    private GetTrainerFromSession getTrainerFromSession;

    @PreAuthorize("hasRole('TRAINER')")
    public HashMap<String, Object> execute() {
        try {
            Long authenticatedTrainerId = getTrainerFromSession.execute();

            if (authenticatedTrainerId == null) {
                return new HashMap<>() {
                    {
                        put("message", "Trainer not found in session");
                        put("error", true);
                    }
                };
            }

            List<TrainmentPlan> trainerPlans = trainmentPlanRepository.findByTrainerId(authenticatedTrainerId);

            // Get unique clients from plans
            List<ClientEntity> uniqueClients = trainerPlans.stream()
                    .map(TrainmentPlan::getClient)
                    .distinct()
                    .collect(Collectors.toList());

            List<ClientResponseDto> responses = uniqueClients.stream()
                    .map(ClientResponseDto::fromEntity)
                    .toList();

            return new HashMap<>() {
                {
                    put("message", "Clients retrieved successfully");
                    put("data", responses);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving clients: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
