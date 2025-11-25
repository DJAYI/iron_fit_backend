package com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.response;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuditorResponseDto(
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

    static public AuditorResponseDto fromEntity(AuditorEntity auditorEntity) {
        return new AuditorResponseDto(
                auditorEntity.getFirstName(),
                auditorEntity.getLastName(),
                auditorEntity.getDocumentType(),
                auditorEntity.getDocumentNumber(),
                auditorEntity.getEmail(),
                auditorEntity.getPhoneNumber(),
                auditorEntity.getUser().getUsername()
        );
    }
}
