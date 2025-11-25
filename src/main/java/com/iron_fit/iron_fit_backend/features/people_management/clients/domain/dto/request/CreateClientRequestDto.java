package com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.request;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public record CreateClientRequestDto(
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
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {

    public ClientEntity toEntity(PasswordEncoder passwordEncoder) {
        ClientEntity client = new ClientEntity();
        client.setFirstName(this.firstName);
        client.setLastName(this.lastName);
        client.setDocumentType(this.documentType);
        client.setDocumentNumber(this.documentNumber);
        client.setEmail(this.email);
        client.setPhoneNumber(this.phoneNumber);

        // Crear usuario asociado
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setIsEnabled(true);
        user.setIsAccountNoExpired(true);
        user.setIsAccountNoLocked(true);
        user.setIsCredentialNoExpired(true);

        RoleEntity role = new RoleEntity();
        role.setName("CLIENT");

        user.setRoleEntities(Set.of(role));
        client.setUser(user);

        return client;
    }
}
