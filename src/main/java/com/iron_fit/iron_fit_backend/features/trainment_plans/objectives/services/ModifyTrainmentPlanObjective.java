package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.requests.UpdateTrainmentPlanObjectiveDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.responses.TrainmentPlanObjectiveResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service

public class ModifyTrainmentPlanObjective {
    @Autowired
    private TrainmentPlanObjectiveRepository trainmentPlanObjectiveRepository;

    public HashMap<String, Object> execute(UpdateTrainmentPlanObjectiveDto updateTrainmentPlanObjectiveDto, Long trainmentObjectiveId) {
        Optional<TrainmentPlanObjective> trainmentPlanObjectiveOptional = trainmentPlanObjectiveRepository.findById(trainmentObjectiveId);

        if (trainmentPlanObjectiveOptional.isEmpty()) return new HashMap<String, Object>() {{
            put("message", "Trainment objective with id " + trainmentObjectiveId + " not found");
            put("error", true);
        }};

        TrainmentPlanObjective newTrainmentObjective = trainmentPlanObjectiveOptional.get();
        newTrainmentObjective.setName(updateTrainmentPlanObjectiveDto.name());

        try {
            TrainmentPlanObjective updatedTrainmentObjective = trainmentPlanObjectiveRepository.save(newTrainmentObjective);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been modified successfully");
                put("trainmentObjective", new TrainmentPlanObjectiveResponseDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be modified successfully");
                put("error", true);
            }};
        }
    }
}
