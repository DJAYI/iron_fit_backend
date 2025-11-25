package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.requests.UpdateMuscularGroupDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.responses.MuscularGroupResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository.MuscularGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service

public class ModifyExistingMuscularGroup {
    @Autowired
    private MuscularGroupRepository muscularGroupRepository;

    public HashMap<String, Object> execute(UpdateMuscularGroupDto updateMuscularGroupDto, Long muscularGroupId) {
        Optional<MuscularGroupEntity> muscularGroupEntity = muscularGroupRepository.findById(muscularGroupId);

        if (muscularGroupEntity.isEmpty()) return new HashMap<String, Object>() {{
            put("message", "Muscular group with id " + muscularGroupId + " not found");
            put("error", true);
        }};

        MuscularGroupEntity newMuscularGroup = muscularGroupEntity.get();
        newMuscularGroup.setName(updateMuscularGroupDto.name());

        try {
            MuscularGroupEntity updatedMuscularGroup = muscularGroupRepository.save(newMuscularGroup);
            return new HashMap<String, Object>() {{
                put("message", "Muscular group has been modified successfully");
                put("muscularGroup", new MuscularGroupResponseDto(newMuscularGroup.getId(), newMuscularGroup.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Muscular group could not be modified successfully");
                put("error", true);
            }};
        }
    }
}
