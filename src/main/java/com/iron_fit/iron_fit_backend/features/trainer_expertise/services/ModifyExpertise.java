package com.iron_fit.iron_fit_backend.features.trainer_expertise.services;

import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.UpdateExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.responses.ExpertiseResponseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service

public class ModifyExpertise {
    @Autowired
    private TrainerExpertiseRepository trainerExpertiseRepository;

    public HashMap<String, Object> execute(UpdateExpertiseDto updateExpertiseDto, Long trainmentObjectiveId) {
        Optional<TrainerExpertise> trainmentPlanObjectiveOptional = trainerExpertiseRepository.findById(trainmentObjectiveId);

        if (trainmentPlanObjectiveOptional.isEmpty()) return new HashMap<String, Object>() {{
            put("message", "Trainment objective with id " + trainmentObjectiveId + " not found");
            put("error", true);
        }};

        TrainerExpertise newTrainmentObjective = trainmentPlanObjectiveOptional.get();
        newTrainmentObjective.setName(updateExpertiseDto.name());

        try {
            TrainerExpertise updatedTrainmentObjective = trainerExpertiseRepository.save(newTrainmentObjective);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been modified successfully");
                put("trainmentObjective", new ExpertiseResponseDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be modified successfully");
                put("error", true);
            }};
        }
    }
}
