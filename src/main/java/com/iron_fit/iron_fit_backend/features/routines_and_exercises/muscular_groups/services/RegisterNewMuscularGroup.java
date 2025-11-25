package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.requests.RegisterMuscularGruopDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.responses.MuscularGroupResponseDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure.repository.MuscularGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterNewMuscularGroup {
    @Autowired
    private MuscularGroupRepository muscularGroupRepository;

    public HashMap<String, Object> execute(RegisterMuscularGruopDto registerMuscularGruopDto) {
        MuscularGroupEntity muscularGroupEntity = new MuscularGroupEntity();
        muscularGroupEntity.setName(registerMuscularGruopDto.name());

        try {

            MuscularGroupEntity newMuscularGroup = muscularGroupRepository.save(muscularGroupEntity);
            return new HashMap<String, Object>() {{
                put("message", "Muscular group has been registered successfully");
                put("muscularGroup", new MuscularGroupResponseDto(newMuscularGroup.getId(), newMuscularGroup.getName()));
            }};
        } catch (Exception e) {
            return new HashMap<String, Object>() {{
                put("message", "Muscular group could not be registered successfully");
                put("error", true);
            }};
        }
    }
}
