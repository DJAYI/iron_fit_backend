package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.responses.TrainmentPlanObjectiveResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveTrainmentPlanObjectives {
    @Autowired
    private TrainmentPlanObjectiveRepository trainmentPlanObjectiveRepository;

    public HashMap<String, Object> execute() {
        try {

            List<TrainmentPlanObjective> trainmentObjectiveEntities = trainmentPlanObjectiveRepository.findAll();

            List<TrainmentPlanObjectiveResponseDto> mappedTrainmentObjectives = trainmentObjectiveEntities.stream().map(trainmentPlanObjective -> new TrainmentPlanObjectiveResponseDto(trainmentPlanObjective.getId(), trainmentPlanObjective.getName())).toList();
            return new HashMap<String, Object>() {{
                put("message", "Trainment objectives has been retrieve successfully");
                put("trainmentObjectives", mappedTrainmentObjectives);
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objectives could not be retrieve");
                put("error", true);
            }};
        }
    }
}
