package com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request.UpdateAuditorRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.response.AuditorResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure.repository.AuditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UpdateAuditorService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditorRepository auditorRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(UpdateAuditorRequestDto updateAuditorRequestDto, Long id){

        Optional<AuditorEntity> foundAuditor = auditorRepository.findById(id);

        if(foundAuditor.isEmpty()){
            return new HashMap<String, Object>() {{
                put("message", "Auditor not found");
                put("error", true);
            }};
        }

        AuditorEntity newAuditor = updateAuditorRequestDto.applyToEntity(foundAuditor.get(), passwordEncoder);

        try {
            auditorRepository.save(newAuditor);
            return new HashMap<String, Object>() {{
                put("message", "Auditor updated successfully");
                put("auditor", AuditorResponseDto.fromEntity(newAuditor));
            }};
        } catch (Exception e) {
            return new  HashMap<String, Object>() {{
                put("message", "Auditor could not be updated");
                put("error", true);
            }};
        }
    }
}
