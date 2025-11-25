package com.iron_fit.iron_fit_backend.features.people_management.shared.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("activateClientService")
public class ActivateClient {

    @Autowired
    private ClientRepository clientRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(Long clientId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElse(null);

            if (client == null) {
                return new HashMap<>() {
                    {
                        put("message", "Client not found");
                        put("error", true);
                    }
                };
            }

            if (client.getUser().getIsEnabled()) {
                return new HashMap<>() {
                    {
                        put("message", "Client is already active");
                    }
                };
            }

            client.getUser().setIsEnabled(true);
            clientRepository.save(client);

            return new HashMap<>() {
                {
                    put("message", "Client activated successfully");
                    put("clientId", clientId);
                    put("username", client.getUser().getUsername());
                }
            };

        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error activating client: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
