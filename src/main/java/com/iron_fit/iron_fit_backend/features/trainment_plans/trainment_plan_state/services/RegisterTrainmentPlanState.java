package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.requests.RegisterTrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.responses.TrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterTrainmentPlanState {
    @Autowired
    private TrainmentPlanStateRepository trainmentPlanStateRepository;

    public HashMap<String, Object> execute(RegisterTrainmentPlanStateDto registerTrainmentPlanStateDto) {
        TrainmentPlanState trainmentPlanState = new TrainmentPlanState();
        trainmentPlanState.setName(registerTrainmentPlanStateDto.name());

        try {

            TrainmentPlanState newTrainmentObjective = trainmentPlanStateRepository.save(trainmentPlanState);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been registered successfully");
                put("trainmentObjective", new TrainmentPlanStateDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be registered successfully");
                put("error", true);
            }};
        }
    }
}
