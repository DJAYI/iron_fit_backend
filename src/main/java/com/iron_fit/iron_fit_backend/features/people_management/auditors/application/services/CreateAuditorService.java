package com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request.CreateAuditorRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.response.AuditorResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure.repository.AuditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CreateAuditorService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuditorRepository auditorRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(CreateAuditorRequestDto createAuditorRequestDto){

         AuditorEntity newAuditor = createAuditorRequestDto.toEntity(passwordEncoder);

         try {
            auditorRepository.save(newAuditor);
            return new HashMap<String, Object>() {{
                put("message", "Auditor created successfully");
                put("auditor", AuditorResponseDto.fromEntity(newAuditor));
            }};
         } catch (Exception e) {
             return new  HashMap<String, Object>() {{
                 put("message", "Auditor could not be created");
                 put("error", true);
             }};
         }
    }
}
