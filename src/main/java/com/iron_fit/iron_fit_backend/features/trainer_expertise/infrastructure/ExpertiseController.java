package com.iron_fit.iron_fit_backend.features.trainer_expertise.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.RegisterExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.dto.requests.UpdateExpertiseDto;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.services.ModifyExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.services.RegisterExpertise;
import com.iron_fit.iron_fit_backend.features.trainer_expertise.services.RetrieveExpertises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/expertises")
public class ExpertiseController {

    @Autowired
    private ModifyExpertise modifyExpertise;

    @Autowired
    private RegisterExpertise registerExpertise;

    @Autowired
    protected RetrieveExpertises retrieveExpertises;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getMuscularGroups(){
        HashMap<String, Object> result = retrieveExpertises.execute();

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> registerNewMuscularGroup(@RequestBody RegisterExpertiseDto registerExpertiseDto) {
        HashMap<String, Object> result = registerExpertise.execute(registerExpertiseDto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMuscularGroup(@PathVariable Long id, @RequestBody UpdateExpertiseDto updateExpertiseDto) {
        HashMap<String, Object> result = modifyExpertise.execute(updateExpertiseDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
