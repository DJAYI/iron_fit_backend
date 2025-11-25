package com.iron_fit.iron_fit_backend.features.people_management.shared.services;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("activateTrainerService")
public class ActivateTrainer {

    @Autowired
    private TrainerRepository trainerRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(Long trainerId) {
        try {
            TrainerEntity trainer = trainerRepository.findById(trainerId).orElse(null);

            if (trainer == null) {
                return new HashMap<>() {
                    {
                        put("message", "Trainer not found");
                        put("error", true);
                    }
                };
            }

            if (trainer.getUser().getIsEnabled()) {
                return new HashMap<>() {
                    {
                        put("message", "Trainer is already active");
                    }
                };
            }

            trainer.getUser().setIsEnabled(true);
            trainerRepository.save(trainer);

            return new HashMap<>() {
                {
                    put("message", "Trainer activated successfully");
                    put("trainerId", trainerId);
                    put("username", trainer.getUser().getUsername());
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error activating trainer: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
