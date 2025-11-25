package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.GetClientFromSession;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("clientUpdateMyProfile")
public class UpdateMyProfile {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetClientFromSession getClientFromSession;

    @PreAuthorize("hasRole('CLIENT')")
    public HashMap<String, Object> execute(String email, String phoneNumber) {
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

            // Update only allowed fields
            if (email != null && !email.isEmpty()) {
                client.setEmail(email);
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                client.setPhoneNumber(phoneNumber);
            }

            ClientEntity updated = clientRepository.save(client);
            ClientResponseDto response = ClientResponseDto.fromEntity(updated);

            return new HashMap<>() {
                {
                    put("message", "Profile updated successfully");
                    put("data", response);
                }
            };
        } catch (Exception e) {
            return new HashMap<>() {
                {
                    put("message", "Error updating profile: " + e.getMessage());
                    put("error", true);
                }
            };
        }
    }
}
