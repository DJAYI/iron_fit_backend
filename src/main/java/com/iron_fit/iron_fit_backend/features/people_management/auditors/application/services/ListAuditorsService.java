package com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.response.AuditorResponseDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.entities.AuditorEntity;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure.repository.AuditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ListAuditorsService {

    @Autowired
    private AuditorRepository auditorRepository;

    @PreAuthorize("hasRole('AUDITOR')")
    public HashMap<String, Object> execute(){
        try {

            List<AuditorEntity> auditorEntities = auditorRepository.findAll();
            List<AuditorResponseDto> auditors = auditorEntities.stream().map(AuditorResponseDto::fromEntity).toList();

            return new HashMap<String, Object>(){{
                put("auditors", auditors);
                put("message", "Auditors list has been successfully retrieved");
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>(){{
                put("message", e.getMessage());
                put("error", true);
            }};
        }
    }
}
