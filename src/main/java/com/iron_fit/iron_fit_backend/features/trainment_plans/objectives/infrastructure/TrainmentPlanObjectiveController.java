package com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.requests.RegisterTrainmentPlanObjectiveDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.domain.dto.requests.UpdateTrainmentPlanObjectiveDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services.ModifyTrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services.RegisterTrainmentPlanObjective;
import com.iron_fit.iron_fit_backend.features.trainment_plans.objectives.services.RetrieveTrainmentPlanObjectives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/trainment-plan/objectives")
public class TrainmentPlanObjectiveController {

    @Autowired
    private ModifyTrainmentPlanObjective modifyTrainmentPlanObjective;

    @Autowired
    private RegisterTrainmentPlanObjective registerTrainmentPlanObjective;

    @Autowired
    protected RetrieveTrainmentPlanObjectives retrieveTrainmentPlanObjectives;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getMuscularGroups(){
        HashMap<String, Object> result = retrieveTrainmentPlanObjectives.execute();

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> registerNewMuscularGroup(@RequestBody RegisterTrainmentPlanObjectiveDto registerTrainmentPlanObjectiveDto) {
        HashMap<String, Object> result = registerTrainmentPlanObjective.execute(registerTrainmentPlanObjectiveDto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMuscularGroup(@PathVariable Long id, @RequestBody UpdateTrainmentPlanObjectiveDto updateTrainmentPlanObjectiveDto) {
        HashMap<String, Object> result = modifyTrainmentPlanObjective.execute(updateTrainmentPlanObjectiveDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
