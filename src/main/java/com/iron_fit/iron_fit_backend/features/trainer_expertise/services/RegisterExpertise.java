package com.iron_fit.iron_fit_backend.features.trainer_expertise.services;

import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.RegisterExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.responses.ExpertiseResponseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterExpertise {
    @Autowired
    private TrainerExpertiseRepository trainerExpertiseRepository;

    public HashMap<String, Object> execute(RegisterExpertiseDto registerExpertiseDto) {
        TrainerExpertise trainerExpertise = new TrainerExpertise();
        trainerExpertise.setName(registerExpertiseDto.name());

        try {

            TrainerExpertise newTrainmentObjective = trainerExpertiseRepository.save(trainerExpertise);
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective has been registered successfully");
                put("trainmentObjective", new ExpertiseResponseDto(newTrainmentObjective.getId(), newTrainmentObjective.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Trainment objective could not be registered successfully");
                put("error", true);
            }};
        }
    }
}
