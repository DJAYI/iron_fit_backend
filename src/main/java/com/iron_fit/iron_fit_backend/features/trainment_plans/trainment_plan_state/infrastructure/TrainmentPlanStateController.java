package com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.infrastructure;

import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.requests.RegisterTrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.domain.dto.requests.UpdateTrainmentPlanStateDto;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services.ModifyTrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services.RegisterTrainmentPlanState;
import com.iron_fit.iron_fit_backend.features.trainment_plans.trainment_plan_state.services.RetrieveTrainmentPlanState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/trainment-plan/states")
public class TrainmentPlanStateController {

    @Autowired
    private ModifyTrainmentPlanState modifyTrainmentPlanState;

    @Autowired
    private RegisterTrainmentPlanState registerTrainmentPlanState;

    @Autowired
    protected RetrieveTrainmentPlanState retrieveTrainmentPlanState;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getMuscularGroups(){
        HashMap<String, Object> result = retrieveTrainmentPlanState.execute();

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping
    public ResponseEntity<?> registerNewMuscularGroup(@RequestBody RegisterTrainmentPlanStateDto registerTrainmentPlanStateDto) {
        HashMap<String, Object> result = registerTrainmentPlanState.execute(registerTrainmentPlanStateDto);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMuscularGroup(@PathVariable Long id, @RequestBody UpdateTrainmentPlanStateDto updateTrainmentPlanStateDto) {
        HashMap<String, Object> result = modifyTrainmentPlanState.execute(updateTrainmentPlanStateDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else  return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
