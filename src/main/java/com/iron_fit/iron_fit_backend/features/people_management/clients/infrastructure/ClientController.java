package com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure;

import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.CreateClientService;
import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.ListClientsService;
import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.UpdateClientService;
import com.iron_fit.iron_fit_backend.features.people_management.clients.application.services.client_profile.*;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.request.CreateClientRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.dto.request.UpdateClientRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.shared.services.ActivateClient;
import com.iron_fit.iron_fit_backend.features.people_management.shared.services.DeactivateClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {
    @Autowired
    private CreateClientService createClientService;

    @Autowired
    private UpdateClientService updateClientService;

    @Autowired
    private ListClientsService listClientsService;

    @Autowired
    private GetMyProfile getMyProfile;

    @Autowired
    private UpdateMyProfile updateMyProfile;

    @Autowired
    private GetMyActivePlan getMyActivePlan;

    @Autowired
    private GetMyRoutines getMyRoutines;

    @Autowired
    private GetMyEvaluations getMyEvaluations;

    @Autowired
    private ActivateClient activateClientService;

    @Autowired
    private DeactivateClient deactivateClientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Valid CreateClientRequestDto createClientRequestDto) {
        HashMap<String, Object> result = createClientService.execute(createClientRequestDto);

        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@RequestBody @Valid UpdateClientRequestDto updateClientRequestDto,
            @PathVariable Long id) {
        HashMap<String, Object> result = updateClientService.execute(updateClientRequestDto, id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        HashMap<String, Object> result = getMyProfile.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        HashMap<String, Object> result = updateMyProfile.execute(email, phoneNumber);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/training-plan")
    public ResponseEntity<?> getMyActivePlan() {
        HashMap<String, Object> result = getMyActivePlan.execute();
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

    @GetMapping("/me/evaluations")
    public ResponseEntity<?> getMyEvaluations() {
        HashMap<String, Object> result = getMyEvaluations.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<?> getClients() {
        HashMap<String, Object> result = listClientsService.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateClient(@PathVariable Long id) {
        HashMap<String, Object> result = activateClientService.execute(id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateClient(@PathVariable Long id) {
        HashMap<String, Object> result = deactivateClientService.execute(id);
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }
}
