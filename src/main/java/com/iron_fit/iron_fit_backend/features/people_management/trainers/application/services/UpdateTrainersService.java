package com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request.UpdateTrainerRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.response.TrainerResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure.repository.TrainerRepository;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure.repository.TrainerExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateTrainersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerExpertiseRepository expertiseRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(UpdateTrainerRequestDto dto, Long id) {

        TrainerEntity trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        // --- Datos básicos ---
        dto.firstName().ifPresent(trainer::setFirstName);
        dto.lastName().ifPresent(trainer::setLastName);
        dto.documentType().ifPresent(trainer::setDocumentType);
        dto.documentNumber().ifPresent(trainer::setDocumentNumber);
        dto.email().ifPresent(trainer::setEmail);
        dto.phoneNumber().ifPresent(trainer::setPhoneNumber);

        // --- Expertises M:N ---
        dto.expertiseIds().ifPresent(ids -> {
            var expertises = expertiseRepository.findAllById(ids);
            trainer.getExpertises().clear();
            trainer.getExpertises().addAll(expertises);
        });

        // --- Usuario asociado ---
        if (trainer.getUser() != null) {
            UserEntity user = trainer.getUser();

            dto.username().ifPresent(user::setUsername);
            dto.password().ifPresent(p -> user.setPassword(passwordEncoder.encode(p)));
            dto.isEnabled().ifPresent(user::setIsEnabled);
            dto.isAccountNoExpired().ifPresent(user::setIsAccountNoExpired);
            dto.isAccountNoLocked().ifPresent(user::setIsAccountNoLocked);

            dto.roles().ifPresent(roleNames -> {
                Set<RoleEntity> roles = roleNames.stream()
                        .map(name -> {
                            RoleEntity r = new RoleEntity();
                            r.setName(name);
                            return r;
                        })
                        .collect(Collectors.toSet());

                user.setRoleEntities(roles);
            });
        }

        trainerRepository.save(trainer);

        return new HashMap<>() {{
            put("message", "Trainer updated successfully");
            put("trainer", TrainerResponseDto.fromEntity(trainer));
        }};
    }
}
