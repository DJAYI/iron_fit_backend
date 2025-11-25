package com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.response;

import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrainerResponseDto(
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

        // Campos del usuario
        @NotBlank(message = "El username es obligatorio")
        String username
) {

    static public TrainerResponseDto fromEntity(TrainerEntity trainerEntity) {
        return new TrainerResponseDto(
                trainerEntity.getFirstName(),
                trainerEntity.getLastName(),
                trainerEntity.getDocumentType(),
                trainerEntity.getDocumentNumber(),
                trainerEntity.getEmail(),
                trainerEntity.getPhoneNumber(),
                trainerEntity.getUser().getUsername()
        );
    }
}
