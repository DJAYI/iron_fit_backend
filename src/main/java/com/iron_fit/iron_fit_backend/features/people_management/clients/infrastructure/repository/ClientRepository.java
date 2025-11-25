package com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    public Optional<ClientEntity> findByUser_Id(UUID userId);
}
