package com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public record CreateAuditorRequestDto(
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

    public AuditorEntity toEntity(PasswordEncoder passwordEncoder) {
        AuditorEntity auditor = new AuditorEntity();
        auditor.setFirstName(this.firstName);
        auditor.setLastName(this.lastName);
        auditor.setDocumentType(this.documentType);
        auditor.setDocumentNumber(this.documentNumber);
        auditor.setEmail(this.email);
        auditor.setPhoneNumber(this.phoneNumber);

        // Crear usuario asociado
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setIsEnabled(true);
        user.setIsAccountNoExpired(true);
        user.setIsAccountNoLocked(true);
        user.setIsCredentialNoExpired(true);

        RoleEntity role = new RoleEntity();
        role.setName("AUDITOR");

        user.setRoleEntities(Set.of(role));
        auditor.setUser(user);

        return auditor;
    }
}
