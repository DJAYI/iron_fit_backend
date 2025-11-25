package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.UserRepository;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTrainerFromSession {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    public Long execute() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> userAuthenticated = userRepository.findByUsername(username);
        if (userAuthenticated.isEmpty())
            throw new UsernameNotFoundException(username);

        try {
            Optional<TrainerEntity> trainerEntityOptional = trainerRepository
                    .findByUser_Id(userAuthenticated.get().getId());
            if (trainerEntityOptional.isEmpty())
                throw new Exception("Not found trainer associated to user authenticated");

            return Long.valueOf(trainerEntityOptional.get().getId());

        } catch (Exception e) {
            return null;
        }
    }
}
