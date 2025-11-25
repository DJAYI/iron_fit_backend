package com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientResponseDto(
        Long id,

        @NotBlank(message = "El nombre es obligatorio") String firstName,

        @NotBlank(message = "El apellido es obligatorio") String lastName,

        @NotNull(message = "El tipo de documento es obligatorio") DocumentTypeEnum documentType,

        @NotBlank(message = "El número de documento es obligatorio") String documentNumber,

        @Email(message = "El email debe tener un formato válido") @NotBlank(message = "El email es obligatorio") String email,

        String phoneNumber,

        // Campos del usuario
        @NotBlank(message = "El username es obligatorio") String username) {

    static public ClientResponseDto fromEntity(ClientEntity clientsEntity) {
        return new ClientResponseDto(
                clientsEntity.getId(),
                clientsEntity.getFirstName(),
                clientsEntity.getLastName(),
                clientsEntity.getDocumentType(),
                clientsEntity.getDocumentNumber(),
                clientsEntity.getEmail(),
                clientsEntity.getPhoneNumber(),
                clientsEntity.getUser().getUsername());
    }
}
