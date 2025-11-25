package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.responses.TrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.entities.TrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure.repository.TrainmentPlanStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveTrainmentPlanState {
    @Autowired
    private TrainmentPlanStateRepository trainmentPlanStateRepository;

    public HashMap<String, Object> execute() {
        try {

            List<TrainmentPlanState> trainmentObjectiveEntities = trainmentPlanStateRepository.findAll();

            List<TrainmentPlanStateDto> mappedTrainmentObjectives = trainmentObjectiveEntities.stream().map(trainmentPlanState -> new TrainmentPlanStateDto(trainmentPlanState.getId(), trainmentPlanState.getName())).toList();
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
