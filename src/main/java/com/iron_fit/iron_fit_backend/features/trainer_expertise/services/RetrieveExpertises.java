package com.iron_fit.iron_fit_backend.features.trainer_expertise.services;

import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.responses.ExpertiseResponseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveExpertises {
    @Autowired
    private TrainerExpertiseRepository trainerExpertiseRepository;

    public HashMap<String, Object> execute() {
        try {

            List<TrainerExpertise> trainmentObjectiveEntities = trainerExpertiseRepository.findAll();

            List<ExpertiseResponseDto> mappedTrainmentObjectives = trainmentObjectiveEntities.stream().map(trainerExpertise -> new ExpertiseResponseDto(trainerExpertise.getId(), trainerExpertise.getName())).toList();
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
