package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.request.CreateClientRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.response.ClientResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CreateClientService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository auditorRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(CreateClientRequestDto createClientRequestDto){

         ClientEntity newClient = createClientRequestDto.toEntity(passwordEncoder);

         try {
            auditorRepository.save(newClient);
            return new HashMap<String, Object>() {{
                put("message", "Client created successfully");
                put("auditor", ClientResponseDto.fromEntity(newClient));
            }};
         } catch (Exception e) {
             return new  HashMap<String, Object>() {{
                 put("message", "Client could not be created");
                 put("error", true);
             }};
         }
    }
}
