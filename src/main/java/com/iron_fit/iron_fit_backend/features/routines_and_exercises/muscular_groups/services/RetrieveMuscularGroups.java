package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.responses.MuscularGroupResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository.MuscularGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RetrieveMuscularGroups {
    @Autowired
    private MuscularGroupRepository muscularGroupRepository;

    public HashMap<String, Object> execute() {
        try {

            List<MuscularGroupEntity> muscularGroupEntities = muscularGroupRepository.findAll();

            List<MuscularGroupResponseDto> mappedMuscularGroups = muscularGroupEntities.stream().map(muscularGroupEntity -> new MuscularGroupResponseDto(muscularGroupEntity.getId(), muscularGroupEntity.getName())).toList();
            return new HashMap<String, Object>() {{
                put("message", "Muscular groups has been retrieve successfully");
                put("muscularGroups", mappedMuscularGroups);
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Muscular groups could not be retrieve");
                put("error", true);
            }};
        }
    }
}
