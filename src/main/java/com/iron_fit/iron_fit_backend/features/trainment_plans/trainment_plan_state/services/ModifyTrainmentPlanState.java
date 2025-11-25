package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.requests.UpdateTrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.responses.TrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service

public class ModifyTrainmentPlanState {
    @Autowired
    private TrainmentPlanStateRepository trainmentPlanStateRepository;

    public HashMap<String, Object> execute(UpdateTrainmentPlanStateDto updateTrainmentPlanStateDto, Long trainmentObjectiveId) {
        Optional<TrainmentPlanState> trainmentPlanObjectiveOptional = trainmentPlanStateRepository.findById(trainmentObjectiveId);

        if (trainmentPlanObjectiveOptional.isEmpty()) return new HashMap<String, Object>() {{
            put("message", "Trainment objective with id " + trainmentObjectiveId + " not found");
            put("error", true);
        }};

        TrainmentPlanState newTrainmentObjective = trainmentPlanObjectiveOptional.get();
        newTrainmentObjective.setName(updateTrainmentPlanStateDto.name());

        try {
            TrainmentPlanState updatedTrainmentObjective = trainmentPlanStateRepository.save(newTrainmentObjective);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been modified successfully");
                put("trainmentObjective", new TrainmentPlanStateDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be modified successfully");
                put("error", true);
            }};
        }
    }
}
