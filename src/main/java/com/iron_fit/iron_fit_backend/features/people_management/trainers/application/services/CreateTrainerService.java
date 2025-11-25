package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request.CreateTrainerRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.response.TrainerResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateTrainerService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerExpertiseRepository expertiseRepository;


    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(CreateTrainerRequestDto createTrainerRequestDto){

         TrainerEntity newTrainer = createTrainerRequestDto.toEntity(passwordEncoder);

         try {
             if (createTrainerRequestDto.expertiseIds().isPresent()) {
                 Set<TrainerExpertise> expertises = createTrainerRequestDto.expertiseIds().get().stream()
                         .map(id -> expertiseRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Expertise no existe: " + id)))
                         .collect(Collectors.toSet());

                 newTrainer.getExpertises().addAll(expertises);
             }

            trainerRepository.save(newTrainer);
            return new HashMap<String, Object>() {{
                put("message", "Client created successfully");
                put("trainer", TrainerResponseDto.fromEntity(newTrainer));
            }};
         } catch (Exception e) {
             System.out.println(e.getMessage());
             return new  HashMap<String, Object>() {{
                 put("message", "Client could not be created");
                 put("error", true);
             }};
         }
    }
}
