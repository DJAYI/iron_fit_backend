package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("clientGetMyProfile")
public class GetMyProfile {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute() {
        try {
            Long authenticatedClientId = getClientFromSession.execute();

            if (authenticatedClientId == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found in session");
                        put("error", true);
                    }
                };
            }

            ClientEntity client = clientRepository.findById(authenticatedClientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            ClientResponseDto response = ClientResponseDto.fromEntity(client);

            return new HashMap<>() {
                {
                    put("message", "Profile retrieved successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error retrieving profile: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
