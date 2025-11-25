package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ListClientsService {

    @Autowired
    private ClientRepository auditorRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(){
        try {

            List<ClientEntity> auditorEntities = auditorRepository.findAll();
            List<ClientResponseDto> clients = auditorEntities.stream().map(ClientResponseDto::fromEntity).toList();

            return new HashMap<String, Object>(){{
                put("clients", clients);
                put("message", "Clients list has been successfully retrieved");
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>(){{
                put("message", e.getMessage());
                put("error", true);
            }};
        }
    }
}
