package com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.RegisterExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities.TrainerExpertise;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public record CreateTrainerRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentTypeEnum documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        String documentNumber,

        @Email(message = "El email debe tener un formato válido")
        @NotBlank(message = "El email es obligatorio")
        String email,

        String phoneNumber,

        Optional<Set<Long>> expertiseIds,

        // Campos del usuario
        @NotBlank(message = "El username es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {

    public TrainerEntity toEntity(PasswordEncoder passwordEncoder) {
        TrainerEntity trainer = new TrainerEntity();
        trainer.setFirstName(this.firstName);
        trainer.setLastName(this.lastName);
        trainer.setDocumentType(this.documentType);
        trainer.setDocumentNumber(this.documentNumber);
        trainer.setEmail(this.email);
        trainer.setPhoneNumber(this.phoneNumber);
        trainer.setExpertises(new LinkedHashSet<>());

        // Crear usuario asociado
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setIsEnabled(true);
        user.setIsAccountNoExpired(true);
        user.setIsAccountNoLocked(true);
        user.setIsCredentialNoExpired(true);

        RoleEntity role = new RoleEntity();
        role.setName("TRAINER");

        user.setRoleEntities(Set.of(role));
        trainer.setUser(user);

        return trainer;
    }
}
