package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.requests.RegisterTrainmentPlanObjectiveDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.responses.TrainmentPlanObjectiveResponseDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.entities.TrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure.repository.TrainmentPlanObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterTrainmentPlanObjective {
    @Autowired
    private TrainmentPlanObjectiveRepository trainmentPlanObjectiveRepository;

    public HashMap<String, Object> execute(RegisterTrainmentPlanObjectiveDto registerTrainmentPlanObjectiveDto) {
        TrainmentPlanObjective trainmentPlanObjective = new TrainmentPlanObjective();
        trainmentPlanObjective.setName(registerTrainmentPlanObjectiveDto.name());

        try {

            TrainmentPlanObjective newTrainmentObjective = trainmentPlanObjectiveRepository.save(trainmentPlanObjective);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been registered successfully");
                put("trainmentObjective", new TrainmentPlanObjectiveResponseDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be registered successfully");
                put("error", true);
            }};
        }
    }
}
