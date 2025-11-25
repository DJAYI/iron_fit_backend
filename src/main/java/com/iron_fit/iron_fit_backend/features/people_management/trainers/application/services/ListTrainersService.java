package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.response.TrainerResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ListTrainersService {

    @Autowired
    private TrainerRepository trainerRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(){
        try {

            List<TrainerEntity> trainerEntity = trainerRepository.findAll();
            List<TrainerResponseDto> trainers = trainerEntity.stream().map(TrainerResponseDto::fromEntity).toList();

            return new HashMap<String, Object>(){{
                put("trainers", trainers);
                put("message", "Trainers list has been successfully retrieved");
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>(){{
                put("message", e.getMessage());
                put("error", true);
            }};
        }
    }
}
