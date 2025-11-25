package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.request.UpdateClientRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UpdateClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(UpdateClientRequestDto updateClientRequestDto, Long id){

        Optional<ClientEntity> foundClient = clientRepository.findById(id);

        if(foundClient.isEmpty()){
            return new HashMap<String, Object>() {{
                put("message", "Client not found");
                put("error", true);
            }};
        }

        ClientEntity newClient = updateClientRequestDto.applyToEntity(foundClient.get(), passwordEncoder);

        try {
            clientRepository.save(newClient);
            return new HashMap<String, Object>() {{
                put("message", "Client updated successfully");
                put("client", ClientResponseDto.fromEntity(newClient));
            }};
        } catch (Exception e) {
            return new  HashMap<String, Object>() {{
                put("message", "Client could not be updated");
                put("error", true);
            }};
        }
    }
}
