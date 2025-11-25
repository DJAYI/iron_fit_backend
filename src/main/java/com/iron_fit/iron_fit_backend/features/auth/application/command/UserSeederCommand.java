package com.iron_fit.iron_fit_backend.features.auth.application.command;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.RoleRepository;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserSeederCommand implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos para evitar duplicados
        if (userRepository.count() == 0) {
            seedRolesAndUsers();
        }
    }

    private void seedRolesAndUsers() {
        // Crear o obtener roles
        RoleEntity roleAUDITOR = getOrCreateRole("AUDITOR");
        RoleEntity roleCLIENT = getOrCreateRole("CLIENT");
        RoleEntity roleTRAINER = getOrCreateRole("TRAINER");

        // Crear usuario admin
        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123")) // Encode la contraseña
                .isAccountNoExpired(true)
                .isAccountNoLocked(true)
                .isEnabled(true)
                .isCredentialNoExpired(true)
                .roleEntities(Set.of(roleAUDITOR))
                .build();

        userRepository.save(adminUser);
    }

    private RoleEntity getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder().name(roleName).build()
                ));
    }
}
