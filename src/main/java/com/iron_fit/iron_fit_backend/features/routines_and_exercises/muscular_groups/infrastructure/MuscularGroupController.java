package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.infrastructure;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.requests.RegisterMuscularGruopDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.dto.requests.UpdateMuscularGroupDto;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services.ModifyExistingMuscularGroup;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services.RegisterNewMuscularGroup;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.services.RetrieveMuscularGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/muscular-groups")
public class MuscularGroupController {

    @Autowired
    private ModifyExistingMuscularGroup modifyExistingMuscularGroup;

    @Autowired
    private RegisterNewMuscularGroup registerNewMuscularGroup;

    @Autowired
    protected RetrieveMuscularGroups retrieveMuscularGroups;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getMuscularGroups(){
        HashMap<String, Object> result = retrieveMuscularGroups.execute();

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> registerNewMuscularGroup(@RequestBody RegisterMuscularGruopDto registerMuscularGruopDto) {
        HashMap<String, Object> result = registerNewMuscularGroup.execute(registerMuscularGruopDto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMuscularGroup(@PathVariable Long id, @RequestBody UpdateMuscularGroupDto updateMuscularGroupDto) {
        HashMap<String, Object> result = modifyExistingMuscularGroup.execute(updateMuscularGroupDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
