package com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.RegisterExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record UpdateTrainerRequestDto(
                Optional<String> firstName,
                Optional<String> lastName,
                Optional<DocumentTypeEnum> documentType,
                Optional<String> documentNumber,
                Optional<String> email,
                Optional<String> phoneNumber,
                Optional<Set<Long>> expertiseIds,
                Optional<String> username,
                Optional<String> password,
                Optional<Boolean> isEnabled,
                Optional<Boolean> isAccountNoExpired,
                Optional<Boolean> isAccountNoLocked,
                Optional<Set<String>> roles) {

}