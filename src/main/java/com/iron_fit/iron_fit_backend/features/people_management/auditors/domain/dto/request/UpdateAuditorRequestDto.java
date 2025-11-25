package com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record UpdateAuditorRequestDto(
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<DocumentTypeEnum> documentType,
        Optional<String> documentNumber,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<String> username,
        Optional<String> password,
        Optional<Boolean> isEnabled,
        Optional<Boolean> isAccountNoExpired,
        Optional<Boolean> isAccountNoLocked,
        Optional<Set<String>> roles) {
    public AuditorEntity applyToEntity(AuditorEntity auditor, PasswordEncoder passwordEncoder) {

        // Aplicar solo los campos que están presentes
        this.firstName.ifPresent(auditor::setFirstName);
        this.lastName.ifPresent(auditor::setLastName);
        this.documentType.ifPresent(auditor::setDocumentType);
        this.documentNumber.ifPresent(auditor::setDocumentNumber);
        this.email.ifPresent(auditor::setEmail);
        this.phoneNumber.ifPresent(auditor::setPhoneNumber);

        // Actualizar el usuario asociado si existe
        if (auditor.getUser() != null) {
            UserEntity user = auditor.getUser();

            this.username.ifPresent(user::setUsername);
            password.ifPresent(s -> user.setPassword(passwordEncoder.encode(s)));
            this.isEnabled.ifPresent(user::setIsEnabled);
            this.isAccountNoExpired.ifPresent(user::setIsAccountNoExpired);
            this.isAccountNoLocked.ifPresent(user::setIsAccountNoLocked);

            // Actualizar roles si se proporcionan
            this.roles.ifPresent(roleNames -> {
                Set<RoleEntity> newRoles = roleNames.stream()
                        .map(roleName -> {
                            RoleEntity role = new RoleEntity();
                            role.setName(roleName);
                            return role;
                        })
                        .collect(Collectors.toSet());
                user.setRoleEntities(newRoles);
            });
        }
        return auditor;
    }
}