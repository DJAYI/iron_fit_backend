package com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
