package com.iron_fit.iron_fit_backend.features.people_management.trainers.infrastructure;

import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request.CreateTrainerRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.dto.request.UpdateTrainerRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.CreateTrainerService;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.ListTrainersService;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.UpdateTrainersService;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.application.services.trainer_scoped.*;
import com.iron_fit.iron_fit_backend.features.people_management.shared.services.ActivateTrainer;
import com.iron_fit.iron_fit_backend.features.people_management.shared.services.DeactivateTrainer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/trainers")
public class TrainerController {
    @Autowired
    private CreateTrainerService createTrainerService;

    @Autowired
    private UpdateTrainersService updateTrainersService;

    @Autowired
    private ListTrainersService listTrainersService;

    @Autowired
    private GetMyClients getMyClients;

    @Autowired
    private GetMyTrainingPlans getMyTrainingPlans;

    @Autowired
    private GetMyRoutines getMyRoutines;

    @Autowired
    private GetClientCompliance getClientCompliance;

    @Autowired
    private ActivateTrainer activateTrainerService;

    @Autowired
    private DeactivateTrainer deactivateTrainerService;

    @PostMapping
    public ResponseEntity<?> createTrainer(@RequestBody @Valid CreateTrainerRequestDto createTrainerRequestDto) {
        HashMap<String, Object> result = createTrainerService.execute(createTrainerRequestDto);

        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrainer(@RequestBody @Valid UpdateTrainerRequestDto updateTrainerRequestDto,
            @PathVariable Long id) {
        HashMap<String, Object> result = updateTrainersService.execute(updateTrainerRequestDto, id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/clients")
    public ResponseEntity<?> getMyClients() {
        HashMap<String, Object> result = getMyClients.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/training-plans")
    public ResponseEntity<?> getMyTrainingPlans() {
        HashMap<String, Object> result = getMyTrainingPlans.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/routines")
    public ResponseEntity<?> getMyRoutines() {
        HashMap<String, Object> result = getMyRoutines.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/clients/{clientId}/compliance")
    public ResponseEntity<?> getClientCompliance(@PathVariable Long clientId) {
        HashMap<String, Object> result = getClientCompliance.execute(clientId);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<?> getTrainers() {
        HashMap<String, Object> result = listTrainersService.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateTrainer(@PathVariable Long id) {
        HashMap<String, Object> result = activateTrainerService.execute(id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateTrainer(@PathVariable Long id) {
        HashMap<String, Object> result = deactivateTrainerService.execute(id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }
}
